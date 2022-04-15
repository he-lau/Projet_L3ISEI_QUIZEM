package fr.example.projet_l3isei_quizem


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment
import fr.example.projet_l3isei_quizem.model.Survey
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class ConvertActivity: AppCompatActivity() {

    private var filename = ""
    private var questionsContent = ArrayList<String>()
    private var questionsResponse = ArrayList<Int>()

    // dimension de l'image
    private val width = 500
    private val height = 500


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert)

        // Toolbar avec retour activity parent
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.convert_toolbar)
        setSupportActionBar(toolbar)
        //toolbar.title = ""
        getSupportActionBar()?.setTitle("")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val convertFormat = intent.getStringExtra("FORMAT")
        val questionnaireIntent = intent.getSerializableExtra("SURVEY") as? Survey

        // TextView qui affiche le nom du fichier
        val convertText = findViewById<TextView>(R.id.convert_text)


        // init les infos necessaire au fichier
        filename = questionnaireIntent?.title?.drop(3).toString()

        // onrecupere le contenu de la question et le nombre de reponses
        questionnaireIntent?.questions?.forEach{
            questionsContent.add(it.content)
            questionsResponse.add(it.nbReponses)
        }


        when (convertFormat) {
            "pdf" -> {
                convertText.text = questionnaireIntent?.title?.drop(3).plus(".pdf")
                try {
                    savePDF()
                } catch (e:FileNotFoundException) {
                    e.printStackTrace()
                }
            }
            "png" -> {
                convertText.text = questionnaireIntent?.title?.drop(3).plus(".jpeg")
                try {
                    savePNG()
                } catch (e:FileNotFoundException) {
                    e.printStackTrace()
                }

            }
            "odt" -> {
                convertText.text = questionnaireIntent?.title?.drop(3).plus(".odt")
                saveODT()
            }
        }


    }

    private fun saveODT() {
        //TODO("saveODT()")
    }


    private fun savePDF() {

        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        val file = File(pdfPath,filename+".pdf")
        //val outputStream = FileOutputStream(file)

        // init document pdf
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        // ajout du titre
        val titleParagraph = Paragraph(filename)

        titleParagraph.setTextAlignment(TextAlignment.CENTER)
        titleParagraph.setFontSize(34f)
        titleParagraph.setMarginBottom(40f)

        // ecriture du titre
        document.add(titleParagraph)

        // String avec toute les questions
        var paragraphQuestionsContent = ""
        // ligne de reponse
        val line = "...........................................................................................\n"


        for (i in 1..questionsContent.count()) {
            // ajoute la question courante avec retour charriot
            paragraphQuestionsContent += i.toString()+". "+questionsContent[i-1]+"\n"
            for (j in 1..questionsResponse[i-1]) {
                // ajoute les lignes de réponses
                paragraphQuestionsContent += line
            }

        }

        // construction du Paragraph pour pouvoir ecrire sur le fichier
        val paragraphCenter = Paragraph(paragraphQuestionsContent)
        paragraphCenter.setTextAlignment(TextAlignment.CENTER)
        paragraphCenter.setFontSize(11f)

        document.add(paragraphCenter)

        // fermeture fichier
        document.close()
        // message
        Toast.makeText(this, "PDF created", Toast.LENGTH_SHORT).show()

    }

    private fun savePNG() {

        //create a file to write bitmap data
        val f = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath, filename+".jpeg")

        //Convert bitmap to byte array
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.WHITE)

        val canvas = Canvas(bitmap)
        val p = Paint()

        p.color = Color.BLACK
        p.textSize = 14f

        canvas.drawText(filename, 20f, 40f, p)

        // String avec toute les questions
        var paragraphQuestionsContent = ""
        // ligne de reponse
        val line = "...........................................................................................\n"


        for (i in 1..questionsContent.count()) {
            // ajoute la question courante avec retour charriot
            paragraphQuestionsContent += i.toString()+". "+questionsContent[i-1]+"\n"
            for (j in 1..questionsResponse[i-1]) {
                // ajoute les lignes de réponses
                paragraphQuestionsContent += line
            }
        }

        p.textSize = 11f

        // position depart des questions
        val x = 80f
        val y = 80f

        val questions = paragraphQuestionsContent.split('\n')


        for (i in 1..questions.count()) {
            canvas.drawText(questions[i-1], x, y+i*12, p)
        }


        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , bos)
        val bitmapdata = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()

        // message
        Toast.makeText(this, " JPEG created", Toast.LENGTH_SHORT).show()
    }

}

