import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Coffee;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.repository.CoffeeRepository;
import ru.vsu.amm.java.repository.LikedCoffeeRepository;
import ru.vsu.amm.java.service.CoffeeService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TestCoffeeService {
    private CoffeeRepository coffeeRepository;
    private LikedCoffeeRepository likedCoffeeRepository;
    private CoffeeService coffeeService;

    @BeforeEach
    void setUp() {
        coffeeRepository = mock(CoffeeRepository.class);
        likedCoffeeRepository = mock(LikedCoffeeRepository.class);
        coffeeService = new CoffeeService(coffeeRepository, likedCoffeeRepository);
    }

    @Test
    void createCoffee() {
        User author = new User();
        author.setId(1L);
        Coffee expectedCoffee = new Coffee("Test Coffee", "Test Description", author);

        coffeeService.create(expectedCoffee.getTitle(), expectedCoffee.getDescription(), expectedCoffee.getAuthor());

        verify(coffeeRepository).save(expectedCoffee);
    }

    @Test
    void deleteCoffee() {
        User author = new User();
        author.setId(1L);
        Coffee coffee = new Coffee("Coffee", "Desc", author);
        coffee.setId(1L);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));

        coffeeService.delete(1L, author);

        verify(coffeeRepository).delete(coffee);
    }

    @Test
    void deleteWhenCoffeeNotFound() {
        User user = new User();
        user.setId(1L);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                coffeeService.delete(1L, user));

        verify(coffeeRepository, never()).delete(any());
    }

    @Test
    void deleteWhenUserIsNotAuthor() {
        User author = new User();
        author.setId(1L);
        User otherUser = new User();
        otherUser.setId(2L);
        Coffee coffee = new Coffee("Coffee", "Desc", author);
        coffee.setId(1L);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));

        assertThrows(ForbiddenException.class, () ->
                coffeeService.delete(1L, otherUser));
        verify(coffeeRepository, never()).delete(any());
    }

    @Test
    void findAll() {
        Coffee coffee1 = new Coffee("Coffee1", "Desc1", new User());
        Coffee coffee2 = new Coffee("Coffee2", "Desc2", new User());
        List<Coffee> expected = List.of(coffee1, coffee2);
        when(coffeeRepository.findAll()).thenReturn(expected);

        List<Coffee> actual = coffeeService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void createLikedCoffee() {
        User user = new User();
        user.setId(1L);
        Coffee coffee = new Coffee("Liked Coffee", "Desc", new User());
        coffee.setId(1L);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));

        coffeeService.createLikedCoffee(1L, user);

        verify(likedCoffeeRepository).save(user, coffee);
    }

    @Test
    void findSetLikedCoffees() {
        User user = new User();
        user.setId(1L);
        Coffee coffee = new Coffee("Liked Coffee", "Desc", new User());
        List<Coffee> coffeeList = List.of(coffee);
        when(likedCoffeeRepository.findByUserId(1L)).thenReturn(coffeeList);

        Set<Coffee> result = coffeeService.findSetLikedCoffees(user);

        assertEquals(1, result.size());
        assertTrue(result.contains(coffee));
    }
    @Test
    void deleteLikedCoffee() {
        User user = new User();
        user.setId(1L);
        Coffee coffee = new Coffee("Coffee", "Desc", new User());
        coffee.setId(1L);
        when(coffeeRepository.findById(1L)).thenReturn(Optional.of(coffee));

        coffeeService.deleteLikedCoffee(1L, user);

        verify(likedCoffeeRepository).delete(user.getId(), coffee.getId());
    }

}
