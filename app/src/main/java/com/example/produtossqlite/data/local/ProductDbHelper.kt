package com.example.produtossqlite.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.produtossqlite.model.Product

class ProductDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "products.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_PRODUCTS = "products"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_PRICE REAL NOT NULL,
                $COLUMN_QUANTITY INTEGER NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun insertProduct(product: Product): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_QUANTITY, product.quantity)
        }

        return db.insert(TABLE_PRODUCTS, null, values)
    }

    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_PRODUCTS ORDER BY $COLUMN_ID DESC",
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val product = Product(
                        id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME)),
                        price = it.getDouble(it.getColumnIndexOrThrow(COLUMN_PRICE)),
                        quantity = it.getInt(it.getColumnIndexOrThrow(COLUMN_QUANTITY))
                    )

                    products.add(product)
                } while (it.moveToNext())
            }
        }

        return products
    }

    fun updateProduct(product: Product): Int {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_QUANTITY, product.quantity)
        }

        return db.update(
            TABLE_PRODUCTS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(product.id.toString())
        )
    }

    fun deleteProduct(id: Int): Int {
        val db = writableDatabase

        return db.delete(
            TABLE_PRODUCTS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }
}