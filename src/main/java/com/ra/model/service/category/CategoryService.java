package com.ra.model.service.category;

import com.ra.model.entity.admin.Category;
import com.ra.model.service.IGenericService;

public interface CategoryService extends IGenericService<Category,Integer> {
    boolean updateStatus(Integer id, boolean newStatus);
}
