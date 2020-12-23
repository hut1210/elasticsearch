package com.example.demo.dao;

import com.example.demo.domain.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/21 16:47
 */
@Repository
public interface StudentRepository extends ElasticsearchRepository<Student,String> {
}
