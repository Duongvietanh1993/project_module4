package com.ra.model.dao.category;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.admin.Category;

public interface CategoryDAO extends IGenericDAO<Category,Integer> {
    boolean updateStatus(Integer id, boolean newStatus);
}
