package br.com.fiap.resource;

import br.com.fiap.bo.ColaboracaoBO;
import br.com.fiap.dto.colaboracao.AtualizarColaboracaoDto;
import br.com.fiap.dto.colaboracao.CadastroColaboracaoDto;
import br.com.fiap.dto.colaboracao.DetalhesColaboracaoDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Colaboracao;
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

@Path("/colaboracoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ColaboracaoResource {
    @Inject
    private ColaboracaoBO colaboracaoBO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Colaboracao> listar() throws SQLException {
        return colaboracaoBO.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws SQLException, EntidadeNaoEncontrada {
        Colaboracao colab = colaboracaoBO.buscar(codigo);
        return Response.ok(mapper.map(colab, DetalhesColaboracaoDto.class)).build();
    }

    @GET
    @Path("/usuarios/{idUsuario}")
    public Response buscarPorUsuario(@PathParam("idUsuario") int id) throws SQLException {
        return Response.ok(colaboracaoBO.buscarPorUsuario(id)).build();
    }

    @GET
    @Path("/projetos/{idProjeto}")
    public Response buscarPorProjeto(@PathParam("idProjeto") int id) throws SQLException {
        return Response.ok(colaboracaoBO.buscarPorProjeto(id)).build();
    }

    @POST
    public Response criar(@Valid CadastroColaboracaoDto dto, @Context UriInfo uriInfo) throws SQLException {
        Colaboracao colab = mapper.map(dto, Colaboracao.class);
        colaboracaoBO.cadastrar(colab);

        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(colab.getCodigo())).build();
        return Response.created(uri).entity(mapper.map(colab, DetalhesColaboracaoDto.class)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int codigo, @Valid AtualizarColaboracaoDto dto) throws SQLException, EntidadeNaoEncontrada {
        Colaboracao colab = mapper.map(dto, Colaboracao.class);
        colab.setCodigo(codigo);
        colaboracaoBO.atualizar(colab);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws SQLException, EntidadeNaoEncontrada {
        colaboracaoBO.remover(codigo);
        return Response.noContent().build();
    }
}
