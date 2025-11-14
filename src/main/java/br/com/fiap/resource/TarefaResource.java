package br.com.fiap.resource;

import br.com.fiap.bo.TarefaBO;
import br.com.fiap.dto.tarefa.AtualizarTarefaDto;
import br.com.fiap.dto.tarefa.CadastroTarefaDto;
import br.com.fiap.dto.tarefa.DetalhesTarefaDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Tarefa;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/tarefas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TarefaResource {

    @Inject
    private TarefaBO tarefaBO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Tarefa> listar() throws SQLException {
        return tarefaBO.listar();
    }
    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException{
        Tarefa tarefa = tarefaBO.buscar(codigo);
        return Response.ok(mapper.map(tarefa, DetalhesTarefaDto.class)).build();
    }
    @GET
    @Path("/projetos/{codigoProjeto}")
    public Response buscarPorProjeto(@PathParam("codigoProjeto") int codigoProjeto) throws SQLException{
        List<Tarefa> tarefas = tarefaBO.buscarPorProjeto(codigoProjeto);
        return Response.ok(tarefas).build();
    }
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        tarefaBO.remover(codigo);
        return Response.noContent().build();
    }
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id")int codigo, @Valid AtualizarTarefaDto dto) throws EntidadeNaoEncontrada, SQLException {
        Tarefa tarefa = mapper.map(dto, Tarefa.class);
        tarefa.setCodigo(codigo);
        tarefaBO.atualizar(tarefa);
        return Response.ok().build();
    }
    @POST
    public Response criar (@Valid CadastroTarefaDto dto, @Context UriInfo uriInfo) throws SQLException {
        Tarefa tarefa = mapper.map(dto, Tarefa.class);
        tarefaBO.cadastrar(tarefa);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(tarefa.getCodigo())).build();
        return Response.created(uri).entity(mapper.map(tarefa, DetalhesTarefaDto.class)).build();
    }
}
