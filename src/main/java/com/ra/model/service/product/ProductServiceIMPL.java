package com.ra.model.service.product;

import com.ra.model.dao.product.ProductDAO;
import com.ra.model.entity.admin.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceIMPL implements ProductService{
    @Autowired
    private ProductDAO productDAO;
    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        return productDAO.saveOrUpdate(product);
    }

    @Override
    public Product findById(Integer integer) {
        return productDAO.findById(integer);
    }

    @Override
    public List<Product> findAllByCategoryId(int id) {
        return productDAO.findAllByCategoryId(id);
    }

    @Override
    public boolean updateStatus(Integer id, boolean newStatus) {
        return productDAO.updateStatus(id, newStatus);
    }
}
