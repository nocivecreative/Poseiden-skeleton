package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.CreateTradeDTO;
import com.nnk.springboot.dto.trade.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    @Transactional(readOnly = true)
    public List<TradeDTO> getAllTrades() {
        return tradeRepository
                .findAll()
                .stream()
                .map(t -> new TradeDTO(
                        t.getTradeId(),
                        t.getAccount(),
                        t.getType(),
                        t.getBuyQuantity()))
                .toList();
    }

    @Transactional
    public void addTrade(CreateTradeDTO dto) {
        Trade trade = new Trade();
        trade.setAccount(dto.getAccount());
        trade.setType(dto.getType());
        trade.setBuyQuantity(dto.getBuyQuantity());
        tradeRepository.save(trade);
    }

    @Transactional(readOnly = true)
    public TradeDTO getTradeById(Integer id) {
        return tradeRepository.findById(id)
                .map(t -> new TradeDTO(
                        t.getTradeId(),
                        t.getAccount(),
                        t.getType(),
                        t.getBuyQuantity()))
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with id: " + id));
    }

    @Transactional
    public void updateTrade(Integer id, TradeDTO dto) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with id: " + id));

        trade.setAccount(dto.getAccount());
        trade.setType(dto.getType());
        trade.setBuyQuantity(dto.getBuyQuantity());

        tradeRepository.save(trade);
    }

    @Transactional
    public void deleteTrade(Integer id) {
        if (!tradeRepository.existsById(id))
            throw new IllegalArgumentException("Trade not found with id: " + id);

        tradeRepository.deleteById(id);
    }
}
