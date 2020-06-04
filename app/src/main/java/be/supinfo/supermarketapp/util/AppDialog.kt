package be.supinfo.supermarketapp.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.ui.main.MainViewModel
import kotlinx.android.synthetic.main.layout_app_dialog2.*
import javax.inject.Inject

const val DIALOG_MESSAGE: String = "message"
const val DIALOG_ID: String = "id"
const val DIALOG_POSITIVE_RID = "positive_rid"
const val DIALOG_NEGATIVE_RID = "negative_rid"


class AppDialog : AppCompatDialogFragment() {
    private var mDialogFragment: DialogEvents? = null
    private lateinit var mainViewModel: MainViewModel

    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button
    private lateinit var ivClose2: ImageButton
    private lateinit var dataLayout: LinearLayout
    private lateinit var tvTitle: TextView
    private lateinit var ivAddToCart: ImageView


    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        App.component.inject(this)

        Log.d("Hey", "onCreate")
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppDialogStyle)
        mainViewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            ).get(MainViewModel::class.java)

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_app_dialog2, container, false)
//        Dimension dimensions = driver.manage().window().getSize();
//        int screenWidth = dimensions.getWidth();
//        int screenHeight = dimensions.getHeight();

        view.minimumWidth = 900
        view.minimumHeight = 450
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = view.findViewById(R.id.ok_dialog2)
        btnCancel = view.findViewById(R.id.cancel_dialog2)
        ivClose2 = view.findViewById(R.id.ivClose2)
        dataLayout = view.findViewById(R.id.tvProductData_dialog2)
        tvTitle = view.findViewById(R.id.tvTitle_dialog2)
        ivAddToCart = view.findViewById(R.id.ivAddToCart)


        val mArguments = arguments
        val dialogId: Int
        val messageString: String?
        var positiveStringId: Int
        var negativeStringId: Int

        if (mArguments != null) {
            dialogId = mArguments.getInt(DIALOG_ID)
            messageString = mArguments.getString(DIALOG_MESSAGE)

            if (dialogId == 0 || messageString == null) {
                throw IllegalArgumentException("DIALOG_ID and/or DIALOG_MESSAGE not present in the bundle")
            }

            positiveStringId = mArguments.getInt(DIALOG_POSITIVE_RID)
            if (positiveStringId == 0) {
                positiveStringId = R.string.ok
            }

            negativeStringId = mArguments.getInt(DIALOG_NEGATIVE_RID)
            if (negativeStringId == 0) {
                negativeStringId = R.string.cancel
            }
        } else {
            throw IllegalArgumentException("Must pass DIALOG_ID and DIALOG_MESSAGE in the bundle")
        }


        when (dialogId) {
            1 -> mainViewModel.selectedProduct.observe(viewLifecycleOwner, Observer {
//                tvProductTitle.text = it.title
//                rbRatingbar_dialog.rating = it.rating.toFloat()
//                tvPrice_dialog.text = "${it.price}€"
//                tvProductDescription.text = it.description
                tvTitle_dialog2.text = it.title
                rbRatingBar_dialog2.rating = it.rating.toFloat()
                tvPrice_dialog2.text = "${it.price}€"
                tvDescription_dialog2.text = it.description
            })

            2 -> {
                dataLayout.visibility = View.GONE
                ivAddToCart.visibility = View.VISIBLE
                tvTitle.text = messageString
            }
        }


        ivClose2.setOnClickListener {
            dismiss()
            mDialogFragment?.onDialogCanceled(dialogId)
            Log.i("Canceling", "Canceling")
        }

        btnOk.setOnClickListener {
            dismiss()
            mDialogFragment?.onPositiveResult(dialogId, mArguments)
        }

        btnCancel.setOnClickListener {
            dismiss()
            mDialogFragment?.onDialogCanceled(dialogId)
        }

        return view
    }


    override fun onAttach(context: Context) {
        mDialogFragment = try {
            parentFragment as DialogEvents
        } catch (e: TypeCastException) {
            try {
                context as DialogEvents
            } catch (e: ClassCastException) {
                throw ClassCastException("Application $context must implement AppDialog.DialogEvents")
            }
        } catch (e: ClassCastException) {
            throw ClassCastException("Fragment $parentFragment must implement AppDialog.DialogEvents")
        }

        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCancel(dialog: DialogInterface) {
        val dialogId = requireArguments().getInt(DIALOG_ID)
        mDialogFragment?.onDialogCanceled(dialogId)
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


    interface DialogEvents {
        fun onPositiveResult(idDialog: Int, args: Bundle)
        fun onNegativeResult(idDialog: Int, args: Bundle)
        fun onDialogCanceled(idDialog: Int)
    }

}