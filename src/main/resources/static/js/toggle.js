function toggleById(id) {
    element = document.getElementById(id);
    element.hidden = !element.hidden;
}

function toggleDisplayById(id) {
    element = document.getElementById(id);

    if (element.style.display != "none")
        element.style.display = "none";
    else 
        element.style.display = "flex";
}