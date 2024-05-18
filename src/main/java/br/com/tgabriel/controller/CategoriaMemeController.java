package br.com.tgabriel.controller;

import br.com.tgabriel.domain.CategoriaMeme;
import br.com.tgabriel.domain.Meme;
import br.com.tgabriel.domain.MemeChecker;
import br.com.tgabriel.service.ICategoriaMemeService;
import br.com.tgabriel.service.IMemeService;
import br.com.tgabriel.service.IUsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Named
@ViewScoped
public class CategoriaMemeController implements Serializable {

    private static final long serialVersionUID = -3508753726177740824L;

    private CategoriaMeme categoriaMeme;

    private Collection<CategoriaMeme> categoriaMemeCollection;

    @Inject
    private ICategoriaMemeService catMemeService;

    @Inject
    private IUsuarioService usuarioService;

    @Inject
    private IMemeService memeService;

    private Boolean isUpdate;

    private LocalDate dataMeme;

    private Integer quantidadeMeme;

    private Set<MemeChecker> memes;

    private Meme memeSelecionado;

    private BigDecimal somaTotal;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            this.categoriaMeme = new CategoriaMeme();
            this.memes = new HashSet<>();
            this.categoriaMemeCollection = catMemeService.buscarTodos();
            this.somaTotal = BigDecimal.ZERO;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar catgorizar os memes "));
        }
    }

    public void cancel() {
        try {
            this.isUpdate = false;
            this.categoriaMeme = new CategoriaMeme();
            this.memes = new HashSet<>();
            this.somaTotal = BigDecimal.ZERO;
            this.dataMeme = null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
        }

    }


    public void edit(CategoriaMeme catmeme) {
        try {
            this.isUpdate = true;
            this.categoriaMeme = this.catMemeService.consultarComCollection(catmeme.getId());
            this.dataMeme = LocalDate.ofInstant(this.categoriaMeme.getDataMeme(), ZoneId.systemDefault());
            this.memes = this.categoriaMeme.getMemeCheckers();
            this.categoriaMeme.recalcularTotalMemes();
            this.somaTotal = this.categoriaMeme.getSomaTotal();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar editar a venda"));
        }

    }

    public void delete(CategoriaMeme catmme) {
        try {
            catMemeService.cancelarInteracao(catmme);
            cancel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar o envio do meme"));
        }

    }

    public void finalizar(CategoriaMeme catmme) {
        try {
            catMemeService.cancelarInteracao(catmme);
            cancel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar baixar o meme"));
        }

    }

    public void add() {
        try {
            categoriaMeme.setDataMeme(dataMeme.atStartOfDay(ZoneId.systemDefault()).toInstant());
            catMemeService.cadastrar(categoriaMeme);
            this.categoriaMemeCollection = catMemeService.buscarTodos();
            cancel();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cadastrar a categoria do meme"));
        }
    }

    public void update() {
        try {
            catMemeService.alterar(this.categoriaMeme);
            this.categoriaMemeCollection = catMemeService.buscarTodos();
            cancel();
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Meme atualiada com sucesso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar o meme"));
        }

    }

    public void adicionarMeme() {
        Optional<MemeChecker> memeOP =
                this.categoriaMeme.getMemeCheckers().stream().filter(memeF -> memeF.getMeme().getCodigo().equals(this.memeSelecionado.getCodigo())).findFirst();

        if (memeOP.isPresent()) {
            MemeChecker memeChecker = memeOP.get();
            memeChecker.adicionar(this.quantidadeMeme);
        } else {
            MemeChecker memeChecker = new MemeChecker();
            memeChecker.setMeme(this.memeSelecionado);
            memeChecker.adicionar(this.quantidadeMeme);
            memeChecker.setVenda(this.categoriaMeme);
            this.categoriaMeme.getMemeCheckers().add(memeChecker);
        }
        this.categoriaMeme.recalcularTotalMemes();
        this.memes = this.categoriaMeme.getMemeCheckers();
        this.somaTotal = this.categoriaMeme.getSomaTotal();
    }

    public void removerMeme() {
        Optional<MemeChecker> memeOP =
                this.categoriaMeme.getMemeCheckers().stream().filter(memeF -> memeF.getMeme().getCodigo().equals(this.memeSelecionado.getCodigo())).findFirst();

        if (memeOP.isPresent()) {
            MemeChecker checker = memeOP.get();
            checker.remover(this.quantidadeMeme);
            if (checker.getQuantidade() == 0 || checker.getQuantidade() < 0) {
                this.categoriaMeme.getMemeCheckers().remove(checker);
            }
            this.categoriaMeme.recalcularTotalMemes();
            this.memes = this.categoriaMeme.getMemeCheckers();
            this.somaTotal = this.categoriaMeme.getSomaTotal();
        }

    }

    public void removerMeme(MemeChecker memeChecker) {

        this.categoriaMeme.getMemeCheckers().remove(memeChecker);
        this.categoriaMeme.recalcularTotalMemes();
        this.memes = this.categoriaMeme.getMemeCheckers();
        this.somaTotal = this.categoriaMeme.getSomaTotal();
    }

    public void onRowEdit(RowEditEvent event) {
        MemeChecker mms = (MemeChecker) event.getObject();
        adicionarOuRemoverMeme(mms);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getClass()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void adicionarOuRemoverMeme(MemeChecker checker) {
        if (checker.getQuantidade() != this.quantidadeMeme) {
            int quantidade =  this.quantidadeMeme - checker.getQuantidade();
            if (quantidade > 0) {
                checker.adicionar(quantidade);
            } else {
                this.memes.remove(checker);
            }
            this.somaTotal = BigDecimal.ZERO;
            this.memes.forEach(meme -> {
                this.somaTotal = this.somaTotal.add(meme.getSomaTotal());
            });
        }
    }

    public CategoriaMeme getCategoriaMeme() {
        return categoriaMeme;
    }

    public void setCategoriaMeme(CategoriaMeme categoriaMeme) {
        this.categoriaMeme = categoriaMeme;
    }

    public Collection<CategoriaMeme> getCategoriaMemeCollection() {
        return categoriaMemeCollection;
    }

    public void setCategoriaMemeCollection(Collection<CategoriaMeme> categoriaMemeCollection) {
        this.categoriaMemeCollection = categoriaMemeCollection;
    }

    public ICategoriaMemeService getCatMemeService() {
        return catMemeService;
    }

    public void setCatMemeService(ICategoriaMemeService catMemeService) {
        this.catMemeService = catMemeService;
    }

    public IUsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public IMemeService getMemeService() {
        return memeService;
    }

    public void setMemeService(IMemeService memeService) {
        this.memeService = memeService;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    public LocalDate getDataMeme() {
        return dataMeme;
    }

    public void setDataMeme(LocalDate dataMeme) {
        this.dataMeme = dataMeme;
    }

    public Integer getQuantidadeMeme() {
        return quantidadeMeme;
    }

    public void setQuantidadeMeme(Integer quantidadeMeme) {
        this.quantidadeMeme = quantidadeMeme;
    }

    public Set<MemeChecker> getMemes() {
        return memes;
    }

    public void setMemes(Set<MemeChecker> memes) {
        this.memes = memes;
    }

    public Meme getMemeSelecionado() {
        return memeSelecionado;
    }

    public void setMemeSelecionado(Meme memeSelecionado) {
        this.memeSelecionado = memeSelecionado;
    }

    public BigDecimal getSomaTotal() {
        return somaTotal;
    }

    public void setSomaTotal(BigDecimal somaTotal) {
        this.somaTotal = somaTotal;
    }
}