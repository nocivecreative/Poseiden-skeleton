package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.request.AddBidListDTO;
import com.nnk.springboot.dto.response.BidListDTO;
import com.nnk.springboot.dto.response.UpdateBidListDTO;
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
    public void addBidList(AddBidListDTO addBidListDTO) {
        BidList bl = new BidList(
                addBidListDTO.getAccount(),
                addBidListDTO.getType(),
                addBidListDTO.getBidQuantity());
        bidListRepository.save(bl);
    }

    @Transactional
    public UpdateBidListDTO getBildListById(Integer id) {

        return bidListRepository.findById(id)
                .map(bl -> new UpdateBidListDTO(
                        bl.getBidListId(),
                        bl.getAccount(),
                        bl.getType(),
                        bl.getBidQuantity()))
                .orElseThrow(() -> new IllegalArgumentException("BidList not found with id: " + id));
    }

    @Transactional
    public void updateBidList(Integer id, UpdateBidListDTO blDTO) {

        if (!bidListRepository.existsById(id))
            throw new IllegalArgumentException("BidList not found with id: " + id);

        BidList bl = new BidList(
                id,
                blDTO.getAccount(),
                blDTO.getType(),
                blDTO.getBidQuantity());

        bidListRepository.save(bl);

    }

    public void deleteBidList(Integer id) {
        if (!bidListRepository.existsById(id))
            throw new IllegalArgumentException("BidList not found with id: " + id);

        bidListRepository.deleteById(id);
    }
}
