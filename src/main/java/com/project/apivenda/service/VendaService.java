package com.project.apivenda.service;

import com.project.apivenda.dto.request.VendaRequest;
import com.project.apivenda.dto.response.VendaResponse;
import com.project.apivenda.entity.Venda;
import com.project.apivenda.exception.ErrorCode;
import com.project.apivenda.exception.exceptions.BusinessException;
import com.project.apivenda.mapper.VendaMapper;
import com.project.apivenda.repository.ClienteRepository;
import com.project.apivenda.repository.VendaRepository;
import com.project.apivenda.service.validator.VendaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaValidator vendaValidator;
    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;

    public VendaResponse findById(Long id) {
        return vendaMapper.toDto(vendaValidator.existsEntityById(id));
    }

    @Transactional(readOnly = true)
    public Page<VendaResponse> search(Pageable pageable, String search, List<Long> statusIds) {

        Page<Venda> clientes;

        try {
            boolean hasSearch = search != null && !search.trim().isEmpty();
            boolean hasStatusFilter = statusIds != null && !statusIds.isEmpty();

            if (hasSearch && hasStatusFilter) {
                clientes = vendaRepository.searchByStatusIds(search.trim(), statusIds, pageable);
            } else if (hasStatusFilter) {
                if (statusIds.size() == 1) {
                    clientes = vendaRepository.findByStatusId(statusIds.getFirst(), pageable);
                } else {
                    clientes = vendaRepository.findByStatusIdIn(statusIds, pageable);
                }
            } else if (hasSearch) {
                clientes = vendaRepository.search(search.trim(), pageable);
            } else {
                clientes = vendaRepository.findAllWithRelations(pageable);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        return clientes.map(vendaMapper::toDto);
    }

    public VendaResponse insert(VendaRequest request) {
        Venda entity =  vendaMapper.toEntity(request);
        return vendaMapper.toDto(vendaRepository.save(entity));
    }

}
