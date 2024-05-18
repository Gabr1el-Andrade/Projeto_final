package br.com.tgabriel.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "TB_CATEGORIA_MEME")
public class CategoriaMeme implements Persistente{

    public enum Status {
        INICIADO, PUBLICADO, CANCELADO;

        public static Status getByName(String value) {
            for (Status status : Status.values()) {
                if (status.name().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="catmeme_seq")
    private Long id;

    @Column(name = "CODIGO", nullable = false, unique = true)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk",
            foreignKey = @ForeignKey(name = "fk_catmeme_usuario"),
            referencedColumnName = "id", nullable = false
    )

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    private Usuario usuario;

    @OneToMany(mappedBy = "catmeme", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemeChecker> memeCheckers;

    @Column(name = "SOMA_TOTAL", nullable = false)
    private BigDecimal somaTotal;

    @Column(name = "DATA_MEME", nullable = false)
    private Instant dataMeme;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_MEME", nullable = false)
    private Status status;

    public CategoriaMeme() {
        memeCheckers = new HashSet<>();
    }

    public void adicionarMeme(Meme meme, Integer quantidade) {
        validarStatus();
        Optional<MemeChecker> op =
                memeCheckers.stream().filter(filter -> filter.getMeme().getCodigo().equals(meme.getCodigo())).findAny();
        if (op.isPresent()) {
            MemeChecker produtpQtd = op.get();
            produtpQtd.adicionar(quantidade);
        } else {
            MemeChecker mms = new MemeChecker();
            mms.setCategoriaMeme(this);
            mms.setMeme(meme);
            mms.adicionar(quantidade);
            memeCheckers.add(mms);
        }
        recalcularTotalMemes();
    }

    public void removerTodosOsMemes() {
        validarStatus();
        memeCheckers.clear();
        somaTotal = BigDecimal.ZERO;
    }

    public Integer getQuantidadeTotalMemes() {
        int result = memeCheckers.stream()
                .reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantidade(), Integer::sum);
        return result;
    }

    public void recalcularTotalMemes() {
        validarStatus();
        BigDecimal somaTotal = BigDecimal.ZERO;
        for (MemeChecker mmr : this.memeCheckers) {
            somaTotal = somaTotal.add(mmr.getSomaTotal());
        }
        this.somaTotal = somaTotal;
    }

    private void validarStatus() {
        if (this.status == Status.PUBLICADO) {
            throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
        }
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<MemeChecker> getMemeCheckers() {
        return memeCheckers;
    }

    public void setMemeCheckers(Set<MemeChecker> memeCheckers) {
        this.memeCheckers = memeCheckers;
    }

    public void setSomaTotal(BigDecimal somaTotal) {
        this.somaTotal = somaTotal;
    }

    public void setMemes(Set<MemeChecker> memeCheckers) {
        this.memeCheckers = memeCheckers;
    }

    public BigDecimal getSomaTotal() {
        return somaTotal;
    }

    public Instant getDataMeme() {
        return dataMeme;
    }

    public void setDataMeme(Instant dataMeme) {
        this.dataMeme = dataMeme;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
