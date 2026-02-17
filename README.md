🌍 GeoTrack System

Aplicación orientada a objetos para gestión y visualización de coordenadas geográficas, diseñada bajo una arquitectura modular que separa la lógica del sistema de la capa de visualización cartográfica.

📌 Descripción

GeoManager Platform es un sistema compuesto por dos módulos principales:

Módulo Backend (Core del sistema)
Gestiona la lógica de negocio, autenticación de usuarios, administración de datos y verificación de correo electrónico.

Módulo de Visualización Geográfica
Aplicación independiente desarrollada en HTML y JavaScript que permite graficar coordenadas en Google Maps para su representación cartográfica dinámica.

🚀 Características principales

Autenticación de usuarios con confirmación por correo electrónico.

Integración con servicio externo de envío de correos (Mailgun).

Uso de RabbitMQ para gestión de colas y procesamiento asíncrono.

Gestión de coordenadas geográficas.

Visualización interactiva en Google Maps.

Arquitectura modular (separación de backend y frontend de mapas).

🛠️ Tecnologías utilizadas

Programación Orientada a Objetos

Java (backend)

HTML5

JavaScript

Google Maps API

Mailgun

RabbitMQ

🏗️ Arquitectura

El sistema se diseñó bajo un enfoque desacoplado:

El backend administra usuarios, datos y procesos internos.

El módulo de mapas consume la información y la representa gráficamente.

RabbitMQ permite manejar procesos de notificación de manera asíncrona.

Mailgun gestiona la validación de correos electrónicos.

Esta estructura mejora la escalabilidad y facilita futuras ampliaciones.

🎯 Objetivo del proyecto

Aplicar principios de Programación Orientada a Objetos y arquitectura modular para desarrollar un sistema escalable que combine gestión de datos, autenticación segura e integración con servicios externos y herramientas de visualización geoespacial.
