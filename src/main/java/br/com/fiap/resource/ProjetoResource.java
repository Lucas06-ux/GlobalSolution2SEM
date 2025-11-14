package br.com.fiap.resource;

import br.com.fiap.bo.ProjetoBO;
import br.com.fiap.dto.projeto.AtualizarProjetoDto;
import br.com.fiap.dto.projeto.CadastroProjetoDto;
import br.com.fiap.dto.projeto.DetalhesProjetoDto;
import br.com.fiap.exception.EntidadeNaoEncontrada;
import br.com.fiap.model.Projeto;
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

@Path("/projetos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjetoResource {

    @Inject
    private ProjetoBO projetoBO;

    @Inject
    private ModelMapper mapper;

    @GET
    public List<Projeto> listar() throws SQLException {
        return projetoBO.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException{
        Projeto projeto = projetoBO.buscar(codigo);
        return Response.ok(mapper.map(projeto, DetalhesProjetoDto.class)).build();
    }
    @GET
    @Path("/usuarios/{codigoUsuario}")
    public Response buscarPorUsuario(@PathParam("codigoUsuario") int codigoUsuario) throws SQLException{
        List<Projeto> projetos = projetoBO.buscarPorUsuario(codigoUsuario);
        return Response.ok(projetos).build();
    }
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int codigo) throws EntidadeNaoEncontrada, SQLException {
        projetoBO.remover(codigo);
        return Response.noContent().build();
    }
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id")int codigo, @Valid AtualizarProjetoDto dto) throws EntidadeNaoEncontrada, SQLException {
        Projeto projeto = mapper.map(dto, Projeto.class);
        projeto.setCodigo(codigo);
        projetoBO.atualizar(projeto);
        return Response.ok().build();
    }
    @POST
    public Response criar (@Valid CadastroProjetoDto dto, @Context UriInfo uriInfo) throws SQLException {
        Projeto projeto = mapper.map(dto, Projeto.class);
        projetoBO.cadastrar(projeto);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(projeto.getCodigo())).build();
        return Response.created(uri).entity(mapper.map(projeto, DetalhesProjetoDto.class)).build();
    }
}
