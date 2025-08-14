from flask import Flask, request, jsonify
from pymongo import MongoClient
from flask_cors import CORS


app = Flask(__name__)
CORS(app)  # Permite llamadas desde cualquier origen

#  Conexión a MongoDB Atlas (reemplaza con tu cadena de conexión)
client = MongoClient("mongodb+srv://navarrorivasj3:Inacap5@smartflora.b3q7h7k.mongodb.net/")
db = client["Test"]  # Nombre de la base de datos
collection = db["Test"]  # Nombre de la colección
test_collection = db["Test"]

#  GET: Obtener todos los datos
@app.route("/items", methods=["GET"])
def get_items():
    items = list(collection.find({}, {"_id": 0}))  # Ocultamos el _id de Mongo
    return jsonify(items)

# POST: Agregar un nuevo dato
@app.route("/items", methods=["POST"])
def add_item():
    data = request.get_json()
    if not data or "name" not in data or "edad" not in data:
        return jsonify({"error": "Faltan campos"}), 400
    collection.insert_one(data)
    return jsonify({"message": "Item agregado con éxito"}), 201

# DELETE: eliminar un item por name y Edad
@app.route("/items", methods=["DELETE"])
def delete_item():
    data = request.get_json()
    name = data.get("name")
    edad = data.get("edad")

    if not name or edad is None:
        return jsonify({"error": "Faltan campos 'name' o 'edad'"}), 400

    result = collection.delete_one({"name": name, "edad": edad})

    if result.deleted_count == 1:
        return jsonify({"mensaje": f"Item con name='{name}' y Edad={edad} eliminado"}), 200
    else:
        return jsonify({"error": "Item no encontrado"}), 404

app.run(debug=True)
