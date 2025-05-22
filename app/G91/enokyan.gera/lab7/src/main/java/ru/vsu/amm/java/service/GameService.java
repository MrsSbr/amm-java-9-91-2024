package ru.vsu.amm.java.service;

import ru.vsu.amm.java.dto.GameViewDto;
import ru.vsu.amm.java.dto.GameViewExtendedDto;
import ru.vsu.amm.java.entity.GameEntity;
import ru.vsu.amm.java.entity.GameResult;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.GameRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameService {
    private static final Logger logger = Logger.getLogger(GameService.class.getName());

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameService() {
        gameRepository = new GameRepository();
        userRepository = new UserRepository();
        logger.log(Level.INFO, "Сервис партий инициализирован");
    }

    public void addGame(long firstPlayersId, long secondPlayersId, GameResult result) {
        logger.log(Level.FINE, MessageFormat.format(
                "Запрос на добавление новой партии с firstPlayersId={0}, secondPlayersId={1}, result={2}",
                firstPlayersId,
                secondPlayersId,
                result)
        );
        UserEntity firstPlayer = userRepository.findById(firstPlayersId);
        UserEntity secondPlayer = userRepository.findById(secondPlayersId);

        if (firstPlayer == null || secondPlayer == null) {
            logger.log(Level.WARNING, MessageFormat.format(
                    "Не найден один или оба пользователя с id=[{0}, {1}]",
                    firstPlayersId,
                    secondPlayersId
            ));
            return;
        }

        GameEntity game = new GameEntity();
        game.setFirstPlayersId(firstPlayer.getId());
        game.setSecondPlayersId(secondPlayer.getId());
        game.setFinished(LocalDateTime.now());
        game.setResult(result);
        game.setFirstPlayersRatingBefore(firstPlayer.getRating());
        game.setSecondPlayersRatingBefore(secondPlayer.getRating());

        updateRatings(firstPlayer, secondPlayer, result);

        game.setFirstPlayersRatingChange(firstPlayer.getRating() - game.getFirstPlayersRatingBefore());
        game.setSecondPlayersRatingChange(secondPlayer.getRating() - game.getSecondPlayersRatingBefore());

        userRepository.update(firstPlayer);
        userRepository.update(secondPlayer);

        gameRepository.create(game);
        logger.log(Level.FINE, MessageFormat.format(
                "Добавлена партия с firstPlayersId={0}, secondPlayersId={1}, result={2}",
                firstPlayersId,
                secondPlayersId,
                result)
        );
    }

    public List<GameViewDto> getLatestGames(long count) {
        logger.log(Level.FINE, MessageFormat.format("Запрос на получение последних {0} партий", count));
        List<GameEntity> games = gameRepository.findLast(count);
        if (games.isEmpty()) {
            logger.log(Level.WARNING, "Не получено ни одной партии");
        } else {
            logger.log(Level.FINE, MessageFormat.format("Получено последних партий: {0}", games.size()));
        }
        return getGameViewDtos(games);
    }

    public GameViewExtendedDto getGameById(long id) {
        logger.log(Level.FINE, MessageFormat.format("Запрос на получение партии с id={0}", id));
        GameEntity game = gameRepository.findById(id);
        if (game == null) {
            logger.log(Level.WARNING, MessageFormat.format("Партия с id={0} не получена", id));
        } else {
            logger.log(Level.FINE, MessageFormat.format("Партия с id={0} получена", id));
        }
        return getGameViewExtendedDto(game);
    }

    public List<GameViewDto> getLatestGamesByUserId(long id, long count) {
        logger.log(Level.FINE, MessageFormat.format(
                "Запрос на получение последних {0} партий пользователя с id={1}",
                count,
                id
        ));
        List<GameEntity> games = gameRepository.findLastByPlayersId(id, count);
        if (games.isEmpty()) {
            logger.log(Level.WARNING, MessageFormat.format("Не получено ни одной игры пользователя с id={0}", id));
        } else {
            logger.log(Level.FINE, MessageFormat.format("Получено последних игр пользователя с id={0}: {1}", id, games.size()));
        }
        return getGameViewDtos(games);
    }

    public boolean deleteGame(long id) {
        logger.log(Level.FINE, MessageFormat.format("Запрос на аннулирование партии с id={0}", id));

        GameEntity game = gameRepository.findById(id);
        if (game == null) {
            logger.log(Level.WARNING, MessageFormat.format("Не найдена игра с id={0}", id));
            return false;
        }

        List<GameEntity> firstPlayersGames = gameRepository.findByPlayersId(game.getFirstPlayersId()).stream()
                .filter(g -> g.getFinished().isAfter(game.getFinished()))
                .toList();
        List<GameEntity> secondPlayersGames = gameRepository.findByPlayersId(game.getSecondPlayersId()).stream()
                .filter(g -> g.getFinished().isAfter(game.getFinished()))
                .toList();

        if (firstPlayersGames.isEmpty() && secondPlayersGames.isEmpty()) {
            UserEntity firstPlayer = userRepository.findById(game.getFirstPlayersId());
            firstPlayer.setRating(game.getFirstPlayersRatingBefore());
            userRepository.update(firstPlayer);

            UserEntity secondPlayer = userRepository.findById(game.getSecondPlayersId());
            secondPlayer.setRating(game.getSecondPlayersRatingBefore());
            userRepository.update(secondPlayer);

            gameRepository.delete(game);

            logger.log(Level.FINE, MessageFormat.format("Удалена партия с id={0}", id));
            return true;
        }

        logger.log(Level.WARNING, MessageFormat.format("Партия с id={0} не удалена", id));
        return false;
    }

    private int getPlayersCoefficient(UserEntity user) {
        if(gameRepository.findByPlayersId(user.getId()).size() < 30) {
            return 40;
        } else if (user.getRating() < 2400d) {
            return 20;
        } else {
            return 10;
        }
    }

    private void updateRatings(UserEntity firstPlayer, UserEntity secondPlayer, GameResult result) {
        double firstPlayerNewRating = firstPlayer.getRating() + getPlayersCoefficient(firstPlayer) *
                (1 - result.getPoints() - 1 / (1 + Math.pow(10d, (secondPlayer.getRating() - firstPlayer.getRating()) / 400d)));
        double secondPlayerNewRating = secondPlayer.getRating() + getPlayersCoefficient(secondPlayer) *
                (result.getPoints() - 1 / (1 + Math.pow(10d, (firstPlayer.getRating() - secondPlayer.getRating()) / 400d)));

        firstPlayer.setRating(firstPlayerNewRating);
        secondPlayer.setRating(secondPlayerNewRating);
    }

    private List<GameViewDto> getGameViewDtos(List<GameEntity> games) {
        return games.stream()
                .map(game -> {
                    UserEntity firstPlayer = userRepository.findById(game.getFirstPlayersId());
                    UserEntity secondPlayer = userRepository.findById(game.getSecondPlayersId());
                    String firstPlayersNickname = firstPlayer.getNickname();
                    String secondPlayersNickname = secondPlayer.getNickname();
                    return new GameViewDto(game.getId(), firstPlayersNickname, secondPlayersNickname, game.getResult().getPoints());
                })
                .toList();
    }

    private GameViewExtendedDto getGameViewExtendedDto(GameEntity game) {
        if (game == null) {
            return null;
        }

        UserEntity firstPlayer = userRepository.findById(game.getFirstPlayersId());
        UserEntity secondPlayer = userRepository.findById(game.getSecondPlayersId());

        String firstPlayersNickname = firstPlayer.getNickname();
        String secondPlayersNickname = secondPlayer.getNickname();

        return new GameViewExtendedDto(
                game.getId(),
                firstPlayersNickname,
                secondPlayersNickname,
                Date.from(game.getFinished().atZone(ZoneId.systemDefault()).toInstant()),
                game.getResult().getPoints(),
                game.getFirstPlayersRatingBefore(),
                game.getSecondPlayersRatingBefore(),
                game.getFirstPlayersRatingChange(),
                game.getSecondPlayersRatingChange()
        );
    }
}
