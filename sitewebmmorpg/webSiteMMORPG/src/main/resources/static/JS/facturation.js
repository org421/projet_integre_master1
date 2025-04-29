document.addEventListener("DOMContentLoaded", () => {
    const appfacture = Vue.createApp({
        data() {
            return {
                message: "Vos Factures de Tokens",
                factures: []
            };
        },
        mounted() {
            fetch('/factures/mes-factures')
                .then(response => response.json())
                .then(data => {
                    this.factures = data;
                });
        }
    });

    appfacture.mount("#appfacture");
});
