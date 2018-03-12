package de.kaysubs.tracker.nyaasi.model;

import java.util.Arrays;

public abstract class MainCategory implements Category {

    private final int id;
    private SubCategory[] subCategories;

    public MainCategory(int id) {
        this.id = id;
    }

    public SubCategory[] getSubCategories() {
        if(subCategories == null) {
            subCategories = Arrays.stream(getClass().getDeclaredFields())
                    .filter(field -> SubCategory.class.isAssignableFrom(field.getType()))
                    .map(field -> {
                        try {
                            return (SubCategory) field.get(this);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException("This should never happen. All fields are public.", e);
                        }
                    }).toArray(SubCategory[]::new);
        }

        return subCategories;
    }

    public int getMainCategoryId() {
        return id;
    }

    public int getSubCategoryId() {
        return 0;
    }

    public SubCategory getSubcategoryFromId(int subId) {
        for (SubCategory subCategory : getSubCategories())
            if (subCategory.getSubCategoryId() == subId)
                return subCategory;

        throw new IllegalArgumentException("Category \"" + this.toString() + "\" has no subcategory with id " + subId);
    }
}
