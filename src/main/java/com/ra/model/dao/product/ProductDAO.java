package com.ra.model.dao.product;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.admin.Category;
import com.ra.model.entity.admin.Product;

import java.util.List;

public interface ProductDAO extends IGenericDAO<Product,Integer> {
    List<Product> findAllByCategoryId(int categoryId);
}
