package net.yixingong.dining.reviews.repository;

import net.yixingong.dining.reviews.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenCategoryObject_whenSave_thenCategory() {
        Category category = Category.builder()
                .name("Italian")
                .description("Italian food")
                .build();

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory).
                isNotNull()
                .hasFieldOrPropertyWithValue("name", "Italian")
                .hasFieldOrPropertyWithValue("description", "Italian food");
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void givenCategory_whenFindById_thenCategory() {
        Category category = Category.builder()
                .name("Italian")
                .description("Italian food")
                .build();
        categoryRepository.save(category);

        Category categoryDB = categoryRepository.findById(category.getId()).get();

        assertThat(categoryDB).isNotNull();
    }

    @Test
    public void givenCategoryList_whenFindAll_thenCategoryList() {
        Category category0 = Category.builder()
                .name("Spanish")
                .description("Spanish food")
                .build();
        Category category1 = Category.builder()
                .name("Thai")
                .description("Thai food")
                .build();

        categoryRepository.save(category0);
        categoryRepository.save(category1);

        List<Category> categories = categoryRepository.findAll();

        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(2);
    }

    @Test
    public void givenCategoryObject_whenUpdate_thenReturnCategory() {
        Category category = Category.builder()
                .name("Italian")
                .description("Italian food")
                .build();
        Category savedCategory = categoryRepository.save(category);
        savedCategory.setName("Thai");
        savedCategory.setDescription("Thai food");
        Category updatedCategory = categoryRepository.save(savedCategory);
        assertThat(updatedCategory.getName()).isEqualTo("Thai");
        assertThat(updatedCategory.getDescription()).isEqualTo("Thai food");
    }

    @Test
    public void givenCategoryObject_whenDelete_thenRemovedCategory() {
        Category category = Category.builder()
                .name("Italian")
                .description("Italian food")
                .build();
        categoryRepository.save(category);

        categoryRepository.delete(category);
        Optional<Category> categoryOptional = categoryRepository.findById(category.getId());

        assertThat(categoryOptional).isEmpty();
    }
}
