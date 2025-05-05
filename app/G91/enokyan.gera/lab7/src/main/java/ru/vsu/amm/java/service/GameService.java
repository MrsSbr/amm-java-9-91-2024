package ru.vsu.amm.java.service;

import ru.vsu.amm.java.dto.GameViewDto;
import ru.vsu.amm.java.dto.GameViewExtendedDto;
import ru.vsu.amm.java.entity.GameEntity;
import ru.vsu.amm.java.entity.GameResult;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.GameRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameService() {
        gameRepository = new GameRepository();
        userRepository = new UserRepository();
    }

    public void addGame(long firstPlayersId, long secondPlayersId, GameResult result) {
        UserEntity firstPlayer = userRepository.findById(firstPlayersId);
        UserEntity secondPlayer = userRepository.findById(secondPlayersId);

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
    }

    public List<GameViewDto> getLatestGames(long count) {
        List<GameEntity> games = gameRepository.findLast(count);
        return getGameViewDtos(games);
    }

    public GameViewExtendedDto getGameById(long id) {
        GameEntity game = gameRepository.findById(id);
        return getGameViewExtendedDto(game);
    }

    public List<GameViewDto> getLatestGamesByUserId(long id, long count) {
        List<GameEntity> games = gameRepository.findLastByPlayersId(id, count);
        return getGameViewDtos(games);
    }

    public boolean deleteGame(long id) {
        GameEntity game = gameRepository.findById(id);

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

            return true;
        }

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
