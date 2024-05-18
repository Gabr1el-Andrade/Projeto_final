package br.com.tgabriel.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

public class MemeChecker {

    @Id
@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="meme_qtd_seq")
private Long id;

        @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
        private Meme meme;

        @Column(name = "quantidade", nullable = false)
        private Integer quantidade;

        @Column(name = "soma_total", nullable = false)
        private BigDecimal somaTotal;

        @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
        @JoinColumn(name = "id_value_fk",
                foreignKey = @ForeignKey(name = "fk_meme_qtd_value"),
                referencedColumnName = "id", nullable = false
        )
        private CategoriaMeme categoriaMeme;

        public MemeChecker() {
            this.quantidade = 0;
            this.somaTotal = BigDecimal.ZERO;
        }



        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }

        public BigDecimal getSomaTotal() {
            return somaTotal;
        }

        public void setSomaTotal(BigDecimal somaTotal) {
            this.somaTotal = somaTotal;
        }

        public Meme getMeme() {
            return meme;
        }

        public void setMeme(Meme meme) {
            this.meme = meme;
        }

        public void setCategoriaMeme(CategoriaMeme categoriaMeme) {
            this.categoriaMeme = categoriaMeme;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public CategoriaMeme getCategoriaMeme() {

            return categoriaMeme;
        }

        public void setVenda (CategoriaMeme categoriaMeme) {
            this.categoriaMeme = categoriaMeme;
        }


        public void adicionar(Integer quantidade) {
            this.quantidade += quantidade;
            BigDecimal novoId = this.meme.getIdPostagem().multiply(BigDecimal.valueOf(quantidade));
            BigDecimal novoTotalMemes = this.somaTotal.add(novoId);
            this.somaTotal = novoTotalMemes;
        }

        public void remover(Integer quantidade) {
            this.quantidade -= quantidade;
            BigDecimal novoId = this.meme.getIdPostagem().multiply(BigDecimal.valueOf(quantidade));
            this.somaTotal = this.somaTotal.subtract(novoId);
        }

}
