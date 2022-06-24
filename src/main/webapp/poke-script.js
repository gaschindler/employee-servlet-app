// grab all the elements from the page to work with
const pokeId = document.getElementById('poke-id');
const respId = document.getElementById('resp-id');
const pokeName = document.getElementById('resp-name');
const pokeImg = document.getElementById('resp-sprite');
const button = document.querySelector('button');

// create a funtion to fetch a poke object
function fetchPokemon() {
    let idNum = pokeId.value; // capturing the value of the input element

    // send a fetch call to the pokeAPI and cocatenate the value of the pokemon we want
    fetch(`https://pokeapi.co/api/v2/pokemon/${idNum}`)
        .then(response => response.json())
        .then(renderPokemon)
}

function renderPokemon(data) {
    pokeName.innerHTML = `Name: ${data.name}`;
    respId.innerHTML = `Id: ${data.id}`;
    pokeImg.setAttribute('src', data.sprites.front_default);
}

button.addEventListener('click', fetchPokemon);