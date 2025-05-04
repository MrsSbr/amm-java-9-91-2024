package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Coffee;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.repository.CoffeeRepository;
import ru.vsu.amm.java.repository.LikedCoffeeRepository;

import java.util.List;
import java.util.Set;

public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    private final LikedCoffeeRepository likedCoffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository, LikedCoffeeRepository likedCoffeeRepository) {
        this.coffeeRepository = coffeeRepository;
        this.likedCoffeeRepository = likedCoffeeRepository;
    }

    public void create(String title, String description, User author) {
        Coffee coffee = new Coffee(title, description, author);
        coffeeRepository.save(coffee);
    }

    public void delete(Long coffeeId, User user) {
        Coffee coffee = coffeeRepository.findById(coffeeId).orElseThrow(()-> new EntityNotFoundException("Coffee not found with id = " + coffeeId));
        if (!user.getId().equals(coffee.getAuthor().getId())) {
            throw new ForbiddenException("This user cannot edit coffee with id =" + coffeeId);
        }

        coffeeRepository.delete(coffee);
    }

    public List<Coffee> findAll() {
        return coffeeRepository.findAll();
    }

    public void createLikedCoffee(Long coffeeId, User user) {
        Coffee coffee = coffeeRepository.findById(coffeeId).orElseThrow(()-> new EntityNotFoundException("Coffee not found with id = " + coffeeId));
        likedCoffeeRepository.save(user, coffee);
    }

    public Set<Coffee> findSetLikedCoffees(User user) {
        return Set.copyOf(likedCoffeeRepository.findByUserId(user.getId()));
    }

    public List<Coffee> findListLikedCoffees(User user) {
        return likedCoffeeRepository.findByUserId(user.getId());
    }

    public void deleteLikedCoffee(Long coffeeId, User user) {
        Coffee coffee = coffeeRepository.findById(coffeeId).orElseThrow(()-> new EntityNotFoundException("Coffee not found with id = " + coffeeId));

        likedCoffeeRepository.delete(user.getId(), coffeeId);
    }
}
