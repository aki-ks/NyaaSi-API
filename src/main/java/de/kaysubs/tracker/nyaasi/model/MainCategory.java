package de.kaysubs.tracker.nyaasi.model;

public interface MainCategory extends Category {
    SubCategory[] getSubCategories();

    int getMainCategoryId();

    default int getSubCategoryId() {
        return 0;
    }

    default SubCategory getSubcategoryFromId(int subId) {
        for (SubCategory subCategory : getSubCategories())
            if (subCategory.getSubCategoryId() == subId)
                return subCategory;

        throw new IllegalArgumentException("Category \"" + this.toString() + "\" has no subcategory with id " + subId);
    }
}