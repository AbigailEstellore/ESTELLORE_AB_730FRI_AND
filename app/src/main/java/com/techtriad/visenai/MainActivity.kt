package com.techtriad.visenai

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import java.io.InputStream

data class Product(var image: Bitmap, var name: String)

class MainActivity : AppCompatActivity(), GestureDetector.OnDoubleTapListener {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2

    private lateinit var productListView: ListView
    private lateinit var productsAdapter: ArrayAdapter<Product>
    private val productsList = mutableListOf<Product>()

    private lateinit var productInputLayout: LinearLayout
    private lateinit var useimg: ImageView
    private lateinit var editTextInput: EditText
    private lateinit var submitProductButton: ImageButton
    private lateinit var addProductButton: ImageView

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productListView = findViewById(R.id.productListView)
        productInputLayout = findViewById(R.id.productInputLayout)
        useimg = findViewById(R.id.useimg)
        editTextInput = findViewById(R.id.editTextInput)
        submitProductButton = findViewById(R.id.submitProductButton)
        addProductButton = findViewById(R.id.addProductButton)

        productsAdapter = ProductAdapter()
        productListView.adapter = productsAdapter

        setupSampleProducts()
        setupProductInput()

        // Initialize GestureDetector
        gestureDetector = GestureDetectorCompat(this, GestureDetector.SimpleOnGestureListener())
        gestureDetector.setOnDoubleTapListener(this)

        // Set touch listener on ListView for gesture detection
        productListView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }

        // Set an item click listener for the ListView
        productListView.setOnItemClickListener { _, _, position, _ ->
            val product = productsList[position]
            // Handle the click event on the product
            Toast.makeText(this, "Clicked: ${product.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSampleProducts() {
        val images = arrayOf(R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5)
        val names = arrayOf("Juicy Burgirl", "Fried Rise and Shine", "Takoyucky", "Samyoung", "Showerma")

        for (i in images.indices) {
            val bitmap = BitmapFactory.decodeResource(resources, images[i])
            productsList.add(Product(bitmap, names[i]))
        }

        productsAdapter.notifyDataSetChanged()
    }

    private fun setupProductInput() {
        addProductButton.setOnClickListener {
            productInputLayout.visibility = View.VISIBLE
        }

        submitProductButton.setOnClickListener {
            val productName = editTextInput.text.toString()
            if (productName.isNotEmpty()) {
                val imageDrawable = useimg.drawable
                if (imageDrawable != null && imageDrawable is BitmapDrawable) {
                    val bitmap = imageDrawable.bitmap
                    productsList.add(Product(bitmap, productName))
                    productsAdapter.notifyDataSetChanged()
                    productInputLayout.visibility = View.GONE
                } else {
                    Toast.makeText(this, "Please select an image.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a product name.", Toast.LENGTH_SHORT).show()
            }
        }

        useimg.setOnClickListener {
            val options = arrayOf("Take Photo", "Choose from Gallery")
            AlertDialog.Builder(this)
                .setTitle("Select Image")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> dispatchTakePictureIntent()
                        1 -> dispatchPickImageIntent()
                    }
                }
                .show()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun dispatchPickImageIntent() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    useimg.setImageBitmap(imageBitmap)
                }
                REQUEST_IMAGE_PICK -> {
                    val selectedImage: Uri = data?.data ?: return
                    val inputStream: InputStream? = contentResolver.openInputStream(selectedImage)
                    val imageBitmap = BitmapFactory.decodeStream(inputStream)
                    useimg.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    // Handle double tap
    override fun onDoubleTap(event: MotionEvent): Boolean {
        val position = productListView.pointToPosition(event.x.toInt(), event.y.toInt())
        if (position != AdapterView.INVALID_POSITION) {
            val product = productsList[position]

            val options = arrayOf("Edit", "Delete")
            AlertDialog.Builder(this)
                .setTitle("Select Action")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> {
                            // Edit product
                            editTextInput.setText(product.name)
                            useimg.setImageBitmap(product.image)
                            productInputLayout.visibility = View.VISIBLE
                            submitProductButton.setOnClickListener {
                                val newProductName = editTextInput.text.toString()
                                if (newProductName.isNotEmpty()) {
                                    product.name = newProductName
                                    productsAdapter.notifyDataSetChanged()
                                    productInputLayout.visibility = View.GONE
                                }
                            }
                            // Set click listener for image view to retake image
                            useimg.setOnClickListener {
                                val editOptions = arrayOf("Retake Photo", "Choose from Gallery")
                                AlertDialog.Builder(this)
                                    .setTitle("Change Image")
                                    .setItems(editOptions) { _, which ->
                                        when (which) {
                                            0 -> dispatchTakePictureIntent() // Retake photo
                                            1 -> dispatchPickImageIntent() // Choose from gallery
                                        }
                                    }
                                    .show()
                            }
                        }
                        1 -> {
                            // Delete product
                            productsList.removeAt(position)
                            productsAdapter.notifyDataSetChanged()
                        }
                    }
                }
                .show()
        }
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        return false
    }

    private inner class ProductAdapter : ArrayAdapter<Product>(this@MainActivity, R.layout.activity_item_product, productsList) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: layoutInflater.inflate(R.layout.activity_item_product, parent, false)

            val product = getItem(position) ?: return view

            val imageView: ImageView = view.findViewById(R.id.productImage)
            val nameTextView: TextView = view.findViewById(R.id.productName)

            imageView.setImageBitmap(product.image)
            nameTextView.text = product.name

            return view
        }
    }


}
