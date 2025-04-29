// Afficher ou masquer les sections du forum
function showSection(sectionId) {
    // Masquer toutes les sections
    const sections = document.querySelectorAll('.content-section');
    sections.forEach(section => {
        section.style.display = 'none';
    });

    // Afficher la section correspondante
    const activeSection = document.getElementById(sectionId);
    if (activeSection) {
        activeSection.style.display = 'block';
    }
}

// Ajouter un événement de recherche (filtrage des sujets)
document.getElementById('search-bar').addEventListener('input', function(e) {
    const searchTerm = e.target.value.toLowerCase();

    // Boucle sur toutes les sections pour filtrer les sujets
    const sections = document.querySelectorAll('.content-section ul li');
    sections.forEach(function(item) {
        const title = item.querySelector('a').textContent.toLowerCase();
        if (title.includes(searchTerm)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
});

// Par défaut, on affiche la première section du forum (Discussions Générales)
document.addEventListener('DOMContentLoaded', function() {
    showSection('generalDiscussions');
});
