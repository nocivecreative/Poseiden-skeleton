package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.CreateTradeDTO;
import com.nnk.springboot.dto.trade.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des Trade.
 */
@Service
@RequiredArgsConstructor
public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;

    /**
     * Retourne la liste de tous les trades.
     *
     * @return liste des trades
     */
    @Transactional(readOnly = true)
    public List<TradeDTO> getAllTrades() {
        return tradeRepository.findAll().stream()
                .map(t -> TradeDTO.builder()
                        .tradeId(t.getTradeId())
                        .account(t.getAccount())
                        .type(t.getType())
                        .buyQuantity(t.getBuyQuantity())
                        .build())
                .toList();
    }

    /**
     * Crée et persiste un nouveau trade.
     *
     * @param dto données du trade à créer
     */
    @Transactional
    public void addTrade(CreateTradeDTO dto) {
        Trade trade = Trade.builder()
                .account(dto.getAccount())
                .type(dto.getType())
                .buyQuantity(dto.getBuyQuantity())
                .build();
        tradeRepository.save(trade);
    }

    /**
     * Retourne un trade par son identifiant.
     *
     * @param id identifiant du trade
     * @return le trade correspondant
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional(readOnly = true)
    public TradeDTO getTradeById(Integer id) {
        return tradeRepository.findById(id)
                .map(t -> TradeDTO.builder()
                        .tradeId(t.getTradeId())
                        .account(t.getAccount())
                        .type(t.getType())
                        .buyQuantity(t.getBuyQuantity())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with id: " + id));
    }

    /**
     * Met à jour un trade existant.
     *
     * @param id  identifiant du trade à modifier
     * @param dto nouvelles données
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void updateTrade(Integer id, TradeDTO dto) {
        tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with id: " + id));

        Trade trade = Trade.builder()
                .tradeId(id)
                .account(dto.getAccount())
                .type(dto.getType())
                .buyQuantity(dto.getBuyQuantity())
                .build();
        tradeRepository.save(trade);
    }

    /**
     * Supprime un trade par son identifiant.
     *
     * @param id identifiant du trade à supprimer
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void deleteTrade(Integer id) {
        if (!tradeRepository.existsById(id))
            throw new IllegalArgumentException("Trade not found with id: " + id);

        tradeRepository.deleteById(id);
    }
}
