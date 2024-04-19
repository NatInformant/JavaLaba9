let apiUrl = '/items';

// Получение списка продуктов
async function getItems() {
    try {
        let response = await fetch(apiUrl);
        let data = await response.json();
        return data;
    } catch (error) {
        console.log(error);
    }
}
//Создание нового продукта
async function addItem() {
    let newItemName = document.getElementById("new_item_name").value;

    try {
        let response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name: newItemName })
        });
        let data = await response.json();

        getItems().then(data => {
            displayList(data);
        });

    } catch (error) {
        console.log(error);
    }
}
// Удаление продукта
async function deleteItem(id) {
    try {
        let response = await fetch(apiUrl + '/' + encodeURIComponent(id), { method: 'DELETE' });
        let data = await response.json();

        getItems().then(data => {
            displayList(data);
        });

    } catch (error) {
        console.log(error);
    }
}
// Отображение списка продуктов
function displayList(items) {
    let list = document.getElementById("list");
    list.innerHTML = "";

    for (let i = 0; i < items.length; i++) {
        let item = items[i];

        let li = document.createElement("li");
        li.appendChild(document.createTextNode(item.name));

        let deleteButton = document.createElement("button");
        deleteButton.appendChild(document.createTextNode("Удалить"));
        deleteButton.onclick = function() {
            deleteItem(item.id);
        };
        li.appendChild(deleteButton);

        let checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = item.bought;
        /*checkbox.onclick = function() {
            editItem(item.id, !item.bought);
        };*/
        li.insertBefore(checkbox, li.firstChild);

        list.appendChild(li);
    }
}

// Обновление списка продуктов на странице при загрузке
getItems().then(data => {
    displayList(data);
});