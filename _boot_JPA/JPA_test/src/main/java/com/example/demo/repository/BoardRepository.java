package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Board;

//JpaRepository<Entity 클래스, pk타입> =>클래스 타입으로 기재
public interface BoardRepository extends JpaRepository<Board, Long>{

}
