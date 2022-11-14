package com.example.controller;

import com.example.pojo.Product;
import com.example.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product")
@ApiOperation("商品服务")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/session")
    @ApiOperation("获取session")
    public String session(HttpServletRequest request) {
        return "session: " + request.getSession().getId() + "  port: " + request.getServerPort();
    }
    /**
     * 查询商品列表
     *
     * @return
     */
    @ApiOperation("获取商品list")
    @RequestMapping("/list")
    public List<Product> selectProductList() {
        return productService.selectProductList();
    }

}