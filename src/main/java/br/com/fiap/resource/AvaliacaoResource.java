package br.com.fiap.resource;

import br.com.fiap.bo.AvaliacaoBO;
import br.com.fiap.dto.avaliacao.AtualizarAvaliacaoDto;
import br.com.fiap.dto.avaliacao.CadastroAvaliacaoDto;
import br.com.fiap.dto.avaliacao.DetalhesAvaliacaoDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Avaliacao;
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

@Path("/avaliacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {
    @Inject
    private AvaliacaoBO avaliacaoBO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Avaliacao> listar() throws SQLException {
        return avaliacaoBO.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws SQLException, EntidadeNaoEncontrada {
        Avaliacao avaliacao = avaliacaoBO.buscar(codigo);
        return Response.ok(mapper.map(avaliacao, DetalhesAvaliacaoDto.class)).build();
    }

    @GET
    @Path("/usuarios/{idUsuario}")
    public Response buscarPorUsuario(@PathParam("idUsuario") int id) throws SQLException {
        return Response.ok(avaliacaoBO.buscarPorUsuario(id)).build();
    }

    @GET
    @Path("/projetos/{idProjeto}")
    public Response buscarPorProjeto(@PathParam("idProjeto") int id) throws SQLException {
        return Response.ok(avaliacaoBO.buscarPorProjeto(id)).build();
    }

    @POST
    public Response criar(@Valid CadastroAvaliacaoDto dto, @Context UriInfo uriInfo) throws Exception {
        Avaliacao avaliacao = mapper.map(dto, Avaliacao.class);
        avaliacaoBO.cadastrar(avaliacao);

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(avaliacao.getCodigo())).build();
        return Response.created(uri).entity(mapper.map(avaliacao, DetalhesAvaliacaoDto.class)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int codigo, @Valid AtualizarAvaliacaoDto dto) throws Exception {
        Avaliacao avaliacao = mapper.map(dto, Avaliacao.class);
        avaliacao.setCodigo(codigo);
        avaliacaoBO.atualizar(avaliacao);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws Exception {
        avaliacaoBO.remover(codigo);
        return Response.noContent().build();
    }
}
