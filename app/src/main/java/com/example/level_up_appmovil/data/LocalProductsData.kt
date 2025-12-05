package com.example.level_up_appmovil.data

import com.example.level_up_appmovil.R
import com.example.level_up_appmovil.data.api.model.Product

object LocalProductsData {

    // Función auxiliar para obtener la ruta (URI) de una imagen en res/drawable
    private fun getUri(resId: Int): String {
        return "android.resource://com.example.level_up_appmovil/$resId"
    }

    // Lista estática con TUS productos
    val products = listOf(
        Product(
            id = 2001, // IDs altos para no chocar con la API
            name = "PlayStation 5",
            category = "Consolas",
            description = "Consola Sony Next-Gen",
            price = 549990.0,
            image = getUri(R.drawable.ps5) // Usamos tu imagen
        ),
        Product(
            id = 2002,
            name = "Control Xbox Series X",
            category = "Accesorios",
            description = "Mando inalámbrico Carbon Black",
            price = 59990.0,
            image = getUri(R.drawable.control_x_box_x)
        ),
        Product(
            id = 2003,
            name = "HyperX Cloud II",
            category = "Audio",
            description = "Sonido envolvente 7.1",
            price = 89990.0,
            image = getUri(R.drawable.hyperx_cloud2)
        ),
        Product(
            id = 2004,
            name = "Mouse Logitech G502",
            category = "Periféricos",
            description = "Sensor HERO 25K",
            price = 45990.0,
            image = getUri(R.drawable.mause_gamer_g502)
        ),
        Product(
            id = 2005,
            name = "Silla Gamer",
            category = "Mobiliario",
            description = "Comodidad ergonómica",
            price = 189990.0,
            image = getUri(R.drawable.silla_orda_gamer)
        ),
        Product(
            id = 2006,
            name = "PC Gamer",
            category = "Computación",
            description = "Torre de alto rendimiento",
            price = 1200000.0,
            image = getUri(R.drawable.pc_gamer)
        ),
        Product(
            id = 2007,
            name = "Catan",
            category = "Juegos de Mesa",
            description = "El juego de colonización",
            price = 42990.0,
            image = getUri(R.drawable.catan)
        ),
        Product(
            id = 2008,
            name = "Carcassonne",
            category = "Juegos de Mesa",
            description = "Juego de estrategia medieval",
            price = 38990.0,
            image = getUri(R.drawable.carcassonne)
        ),
        Product(
            id = 2009,
            name = "Polerón Level Up",
            category = "Ropa",
            description = "Merchandising oficial",
            price = 35990.0,
            image = getUri(R.drawable.poleron_lvl_up)
        ),
        Product(
            id = 2010,
            name = "Mouse Pad Gamer",
            category = "Accesorios",
            description = "Superficie optimizada",
            price = 25990.0,
            image = getUri(R.drawable.mause_pad_negro)
        )
    )
}