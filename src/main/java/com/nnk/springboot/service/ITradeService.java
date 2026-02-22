package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.trade.CreateTradeDTO;
import com.nnk.springboot.dto.trade.TradeDTO;

/**
 * Contrat métier pour la gestion des Trade.
 */
public interface ITradeService {

    List<TradeDTO> getAllTrades();

    TradeDTO getTradeById(Integer id);

    void addTrade(CreateTradeDTO dto);

    void updateTrade(Integer id, TradeDTO dto);

    void deleteTrade(Integer id);
}
