package edu.first.tipper

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val INI_TIP=25
class MainActivity : AppCompatActivity()
{
    private lateinit var amountInput: EditText
    private lateinit var percentageBar: SeekBar
    private lateinit var tipResult: TextView
    private lateinit var finalAmount: TextView
    private lateinit var percentLabel: TextView
    private lateinit var tipReview: TextView
    private lateinit var checkBoxSplit: CheckBox
    private lateinit var splitAmt: EditText
    private lateinit var splitResult: TextView
    private lateinit var splitedAmt: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        amountInput=findViewById(R.id.amountInput)
        percentageBar=findViewById(R.id.percentageBar)
        tipResult=findViewById(R.id.tipResult)
        finalAmount=findViewById(R.id.finalAmount)
        percentLabel=findViewById(R.id.percentLabel)
        tipReview=findViewById(R.id.tipReview)
        splitAmt=findViewById(R.id.splitAmt)
        checkBoxSplit=findViewById(R.id.checkBoxSplit)
        splitResult=findViewById(R.id.splitResult)
        splitedAmt=findViewById(R.id.splitedAmt)


        percentageBar.progress= INI_TIP
        percentLabel.text="$INI_TIP%"
        updateTipReview(INI_TIP)

        checkBoxSplit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) 
            {
                splitAmt.visibility=View.VISIBLE
                splitedAmt.visibility=View.VISIBLE
                splitResult.visibility=View.VISIBLE
            }
            else
            {
                splitAmt.visibility=View.INVISIBLE
                splitedAmt.visibility=View.INVISIBLE
                splitResult.visibility=View.INVISIBLE
            }
        }
        
        percentageBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                percentLabel.text="$progress%"
                computeResult()

                updateTipReview(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        splitAmt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeResult()
            }
        })


        amountInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                computeResult()

            }
        })
    }
    private fun updateTipReview(tipProgress: Int) {

        val review = when(tipProgress)
        {
            in 0..15-> "Good!!"
            in 16..30-> "Better !!"
            in 31..50-> "Best !!"
            else -> {""}
        }
        tipReview.text=review

    }
    @SuppressLint("SetTextI18n")
    private fun computeResult()
    {

        if(amountInput.text.isEmpty())
        {
            tipResult.text = ""
            finalAmount.text= ""
            splitResult.text= ""
            return
        }
        var splitAmount=0.0
        if(splitAmt.text.isEmpty())
        {
            splitResult.text=""
        }
        else
        {
            splitAmount=splitAmt.text.toString().toDouble()
        }
        val baseAmt = amountInput.text.toString().toDouble()
        val perTip = percentageBar.progress
        val tipAmt = baseAmt * perTip/100
        val finalAmt = baseAmt + tipAmt

        tipResult.text = "%.2f".format(tipAmt)
        finalAmount.text= "%.2f".format(finalAmt)

        val opSplit = if (splitAmount >=2.0) finalAmt / splitAmount else Double.NaN
        if (opSplit.isFinite()) {
            splitResult.text = "%.2f".format(opSplit)
        }
        else
        {
            splitResult.text = "How lonely :("
        }
    }

}