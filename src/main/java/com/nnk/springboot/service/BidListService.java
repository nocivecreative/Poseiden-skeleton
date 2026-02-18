package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidlist.BidListDTO;
import com.nnk.springboot.dto.bidlist.CreateBidListDTO;
import com.nnk.springboot.repositories.BidListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidListService {

    private final BidListRepository bidListRepository;

    @Transactional(readOnly = true)
    public List<BidListDTO> getAllBidList() {
        return bidListRepository.findAll().stream()
                .map(bl -> BidListDTO.builder()
                        .bidListId(bl.getBidListId())
                        .account(bl.getAccount())
                        .type(bl.getType())
                        .bidQuantity(bl.getBidQuantity())
                        .build())
                .toList();
    }

    @Transactional
    public void addBidList(CreateBidListDTO createBidListDTO) {
        BidList bl = BidList.builder()
                .account(createBidListDTO.getAccount())
                .type(createBidListDTO.getType())
                .bidQuantity(createBidListDTO.getBidQuantity())
                .build();
        bidListRepository.save(bl);
    }

    @Transactional(readOnly = true)
    public BidListDTO getBidListById(Integer id) {
        return bidListRepository.findById(id)
                .map(bl -> BidListDTO.builder()
                        .bidListId(bl.getBidListId())
                        .account(bl.getAccount())
                        .type(bl.getType())
                        .bidQuantity(bl.getBidQuantity())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("BidList not found with id: " + id));
    }

    @Transactional
    public void updateBidList(Integer id, BidListDTO blDTO) {
        bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BidList not found with id: " + id));

        BidList bl = BidList.builder()
                .bidListId(id)
                .account(blDTO.getAccount())
                .type(blDTO.getType())
                .bidQuantity(blDTO.getBidQuantity())
                .build();

        bidListRepository.save(bl);
    }

    @Transactional
    public void deleteBidList(Integer id) {
        if (!bidListRepository.existsById(id))
            throw new IllegalArgumentException("BidList not found with id: " + id);

        bidListRepository.deleteById(id);
    }
}
