from flask import Flask, request, jsonify  
from pymongo import MongoClient  
from werkzeug.security import generate_password_hash, check_password_hash  

app = Flask(__name__)  
#  Conexión a MongoDB Atlas (reemplaza con tu cadena de conexión)
client = MongoClient("mongodb+srv://navarrorivasj3:Inacap5@smartflora.b3q7h7k.mongodb.net/")
db = client["Inicio_Sesion"]  # Nombre de la base de datos
collection = db["user_collection"]  # Nombre de la colección
users_collection = db["users"]

#apartado de registro de usuarios

@app.route('/api/register', methods=['POST'])  
def register():  
    data = request.get_json()  
    email = data.get('email')  
    password = data.get('password')  
    users_collection = db["users"]

    # Validaciones básicas  
    if not email or not password:  
        return jsonify({"error": "Email y contraseña son requeridos"}), 400  

    if users_collection.find_one({"email": email}):  
        return jsonify({"error": "El usuario ya existe"}), 409  

    # Encriptar contraseña  
    hashed_password = generate_password_hash(password)  

    # Guardar en MongoDB  
    user_id = users_collection.insert_one({  
        "email": email,  
        "password": hashed_password  
    }).inserted_id  

    return jsonify({"message": "Usuario creado", "id": str(user_id)}), 201  

#Apartado Inicio de Sesion

@app.route('/api/login', methods=['POST'])  
def login():  
    data = request.get_json()  
    email = data.get('email')  
    password = data.get('password')  

    user = users_collection.find_one({"email": email})  

    if not user or not check_password_hash(user["password"], password):  
        return jsonify({"error": "Credenciales inválidas"}), 401  

    return jsonify({"message": "Login exitoso", "user_id": str(user["_id"])}), 200  

import secrets  

#Apartado de recuperación de contraseña

@app.route('/api/forgot-password', methods=['POST'])
def forgot_password():
    data = request.get_json()
    email = data.get('email')
    
    # Busca al usuario por email (no por token)
    user = users_collection.find_one({"email": email})  # ← Clave aquí
    
    if not user:
        return jsonify({"error": "Email no registrado"}), 404

    # Genera y guarda el token en el usuario
    reset_token = secrets.token_urlsafe(32)
    users_collection.update_one(
        {"email": email},  # ← Busca por email, no por _id
        {"$set": {"reset_token": reset_token}}
    )

    # Debug: Imprime el token en consola (para pruebas en Postman)
    print(f"Token para {email}: {reset_token}")
    
    return jsonify({"message": "Token generado. Revisa tu email.", "token": reset_token}), 200

#Apartado de restauración de contraseña

@app.route('/api/reset-password', methods=['POST'])
def reset_password():
    data = request.get_json()
    token = data.get('token')
    new_password = data.get('new_password')

    # Busca al usuario por el token (no por email)
    user = users_collection.find_one({"reset_token": token})  # ← Clave aquí
    
    if not user:
        return jsonify({"error": "Token inválido o expirado"}), 400

    # Actualiza la contraseña y elimina el token
    hashed_password = generate_password_hash(new_password)
    users_collection.update_one(
        {"reset_token": token},
        {
            "$set": {"password": hashed_password},
            "$unset": {"reset_token": ""}  # Elimina el token después de usarlo
        }
    )
    
    return jsonify({"message": "Contraseña actualizada"}), 200 

#apartado de eliminar ususarios
@app.route('/api/delete-account', methods=['DELETE'])
def delete_account():
    data = request.get_json()
    email = data.get('email')
    password = data.get('password')  # Validación opcional para seguridad

    if not email:
        return jsonify({"error": "Email es requerido"}), 400

    # Busca al usuario y verifica credenciales (opcional)
    user = users_collection.find_one({"email": email})
    if not user:
        return jsonify({"error": "Usuario no encontrado"}), 404

    # Opcional: Validar contraseña antes de eliminar
    if password and not check_password_hash(user["password"], password):
        return jsonify({"error": "Contraseña incorrecta"}), 401

    # Elimina el usuario
    users_collection.delete_one({"email": email})

    return jsonify({"message": "Cuenta eliminada exitosamente"}), 200


@app.route('/api/test', methods=['GET'])
def test():
    return jsonify({"status": "OK"})

if __name__ == '__main__':
    app.run(port=5000)  # Usa otro puerto
    print("Rutas registradas:")
for rule in app.url_map.iter_rules():
    print(rule)
