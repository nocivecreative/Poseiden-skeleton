package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.bidlist.BidListDTO;
import com.nnk.springboot.dto.bidlist.CreateBidListDTO;

/**
 * Contrat métier pour la gestion des BidList.
 */
public interface IBidListService {

    List<BidListDTO> getAllBidList();

    BidListDTO getBidListById(Integer id);

    void addBidList(CreateBidListDTO createBidListDTO);

    void updateBidList(Integer id, BidListDTO blDTO);

    void deleteBidList(Integer id);
}
