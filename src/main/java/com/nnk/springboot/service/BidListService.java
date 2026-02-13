package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidList.BidListDTO;
import com.nnk.springboot.dto.BidList.CreateBidListDTO;
import com.nnk.springboot.repositories.BidListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidListService {

    private final BidListRepository bidListRepository;

    @Transactional(readOnly = true)
    public List<BidListDTO> getAllBidList() {
        return bidListRepository
                .findAll()
                .stream()
                .map(bl -> new BidListDTO(
                        bl.getBidListId(),
                        bl.getAccount(),
                        bl.getType(),
                        bl.getBidQuantity()))
                .toList();
    }

    @Transactional
    public void addBidList(CreateBidListDTO createBidListDTO) {
        BidList bl = new BidList(
                createBidListDTO.getAccount(),
                createBidListDTO.getType(),
                createBidListDTO.getBidQuantity());
        bidListRepository.save(bl);
    }

    @Transactional(readOnly = true)
    public BidListDTO getBidListById(Integer id) {
        return bidListRepository.findById(id)
                .map(bl -> new BidListDTO(
                        bl.getBidListId(),
                        bl.getAccount(),
                        bl.getType(),
                        bl.getBidQuantity()))
                .orElseThrow(() -> new IllegalArgumentException("BidList not found with id: " + id));
    }

    @Transactional
    public void updateBidList(Integer id, BidListDTO blDTO) {
        BidList bl = bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BidList not found with id: " + id));

        bl.setAccount(blDTO.getAccount());
        bl.setType(blDTO.getType());
        bl.setBidQuantity(blDTO.getBidQuantity());

        bidListRepository.save(bl);
    }

    @Transactional
    public void deleteBidList(Integer id) {
        if (!bidListRepository.existsById(id))
            throw new IllegalArgumentException("BidList not found with id: " + id);

        bidListRepository.deleteById(id);
    }
}
