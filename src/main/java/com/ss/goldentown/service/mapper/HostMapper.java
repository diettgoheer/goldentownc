package com.ss.goldentown.service.mapper;

import com.ss.goldentown.domain.*;
import com.ss.goldentown.service.dto.HostDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Host and its DTO HostDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface HostMapper {

    @Mapping(source = "person.id", target = "personId")
    HostDTO hostToHostDTO(Host host);

    List<HostDTO> hostsToHostDTOs(List<Host> hosts);

    @Mapping(source = "personId", target = "person")
    Host hostDTOToHost(HostDTO hostDTO);

    List<Host> hostDTOsToHosts(List<HostDTO> hostDTOs);
}
