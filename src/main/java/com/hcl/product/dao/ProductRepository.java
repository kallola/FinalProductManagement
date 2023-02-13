package com.hcl.product.dao;

import com.hcl.product.bo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
//@Query(value = "SELECT p.id,p.name,p.desciption,p.type,p.price FROM PRODUCT p WHERE p.name LIKE %:name% or p.type Like %:type% or p.price between :min and :max",nativeQuery = true)
//@Query(value = "SELECT p.id,p.name,p.desciption,p.type,p.price FROM PRODUCT p WHERE p.name LIKE %:name% or p.name NOT LIKE %_% And p.type Like %:type% or p.type NOT LIKE %_% And p.price between :min and :max",nativeQuery = true)
//@Query(value = "SELECT p.id,p.name,p.desciption,p.type,p.price FROM PRODUCT p WHERE p.name LIKE %:name% or %_% And p.type Like %:type% or %_% And p.price between :min and :max",nativeQuery = true)
/*@Query(value = "SELECT p.id,p.name,p.desciption,p.type,p.price FROM PRODUCT p " +
        "WHERE p.name LIKE (CASE WHEN :name IS NULL THEN '%' ELSE :name END) " +
        "And p.type LIKE (CASE WHEN :type IS NULL THEN '%' ELSE :type  END) " +
        "And p.price between :min and :max",
        nativeQuery = true)*/
@Query(value = "SELECT p.id,p.name,p.desciption,p.type,p.price FROM PRODUCT p " +
        "WHERE p.name LIKE (CASE WHEN :name IS NULL THEN '%' ELSE :name END) " +
        "And p.type LIKE (CASE WHEN :type IS NULL THEN '%' ELSE :type  END) " +
        "And  p.price between :min and :max",
        nativeQuery = true)
public List<Product> searchByProductLike(@Param("name") String name,
                                             @Param("type") String type,
                                             @Param("min") double min,
                                             @Param("max") double max);
   /* @Query("select new com.hcl.product.bo(p.id,p.name,p.desciption,p.type,p.price)" +
            " from Product p" +
            "where p.name like ?1 " +
            "and p.type like (CASE WHEN ?2 IS NULL THEN '%' ELSE ?2 END)")*/
/*@Query("select new com.my.dto(e.name, e.age, e.address)" +
        " from produ e" +
        "where e.name like ?1 " +
        "and e.age like ?2  " +
        "and e.street like (CASE WHEN ?3 IS NULL THEN '%' ELSE ?3 END)")
public List<Product> searchByProductLike(String name, String type, double min, double max);*/
}
