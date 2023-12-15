package com.ra.model.service.product;

import com.ra.model.entity.admin.Product;
import com.ra.model.service.IGenericService;

import java.util.List;

public interface ProductService extends IGenericService<Product,Integer> {
    List<Product> findAllByCategoryId(int categoryId);
}
