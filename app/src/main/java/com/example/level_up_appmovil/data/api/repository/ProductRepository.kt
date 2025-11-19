package com.example.level_up_appmovil.data.api.repository

import com.example.level_up_appmovil.data.api.model.Product
import com.example.level_up_appmovil.R
class ProductRepository {
    fun getSampleProducts(): List<Product> {
        return listOf(
            Product("JM001", "Catan", "Juegos de Mesa", "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.", 29990.0, R.drawable.catan),
            Product("JM002", "Carcassonne", "Juegos de Mesa", "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.", 24990.0, R.drawable.carcassonne),
            Product("AC001", "Controlador Inalámbrico Xbox Series X", "Accesorios", "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.", 59990.0, R.drawable.control_x_box_x),
            Product("AC002", "Auriculares Gamer HyperX Cloud II", "Accesorios", "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.", 79990.0, R.drawable.hyperx_cloud2),
            Product("CO001", "PlayStation 5", "Consolas", "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.", 549990.0, R.drawable.ps5),
            Product("CG001", "PC Gamer ASUS ROG Strix", "Computadores Gamers", "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.", 1299990.0, R.drawable.pc_gamer),
            Product("SG001", "Silla Gamer Secretlab Titan", "Sillas Gamers", "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.", 349990.0, R.drawable.silla_orda_gamer),
            Product("MS001", "Mouse Gamer Logitech G502 HERO", "Mouse", "Con sensor de alta precisión y botones personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.", 49990.0, R.drawable.mause_gamer_g502),
            Product("MP001", "Mousepad Razer Goliathus Extended Chroma", "Mousepad", "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.", 29990.0, R.drawable.mause_pad_negro),
            Product("PP001", "Poleron Gamer Personalizado 'Level-Up'", "Poleron Personalizados", "Un poleron cómodo y estilizada, con la posibilidad de personalizarlo con tu gamer tag o diseño favorito.", 14990.0, R.drawable.poleron_lvl_up)
        )
    }
}
