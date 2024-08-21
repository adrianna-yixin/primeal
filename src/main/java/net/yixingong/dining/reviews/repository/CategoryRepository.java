package net.yixingong.dining.reviews.repository;

import net.yixingong.dining.reviews.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
