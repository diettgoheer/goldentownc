package com.ss.goldentown.service.mapper;

import com.ss.goldentown.domain.*;
import com.ss.goldentown.service.dto.AgentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Agent and its DTO AgentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class,})
public interface AgentMapper {

   
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "father.id", target = "fatherId")
    AgentDTO agentToAgentDTO(Agent agent);

    List<AgentDTO> agentsToAgentDTOs(List<Agent> agents);

  
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "fatherId", target = "father")
    Agent agentDTOToAgent(AgentDTO agentDTO);

    List<Agent> agentDTOsToAgents(List<AgentDTO> agentDTOs);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default Agent agentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setId(id);
        return agent;
    }
}
