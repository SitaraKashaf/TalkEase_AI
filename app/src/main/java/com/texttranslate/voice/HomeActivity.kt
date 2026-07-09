package com.texttranslate.voice

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.texttranslate.voice.base.PermissionHelperActivity
import com.texttranslate.voice.databinding.ActivityHomeBinding
import com.texttranslate.voice.utils.Utils

class HomeActivity : PermissionHelperActivity(), View.OnClickListener {
    private var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.getRoot())
        
        binding!!.mTxtTitle.paint.setShader(
            Utils.getShader(
                binding!!.mTxtTitle
            )
        )

        binding!!.mIVVoice.setOnClickListener(this)
        binding!!.mIVText.setOnClickListener(this)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false
                    showExitDialog(this@HomeActivity)
                }
            }
        })
    }

    fun showExitDialog(context: Context) {
        val dialogLogOut = Dialog(context)
        dialogLogOut.setContentView(R.layout.activity_exit)
        dialogLogOut.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogLogOut.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLogOut.window?.setGravity(Gravity.BOTTOM)
        dialogLogOut.setCanceledOnTouchOutside(true)
        
        val btnyes = dialogLogOut.findViewById<TextView>(R.id.yes)
        val btnno = dialogLogOut.findViewById<TextView>(R.id.no)

        // click listener for Yes
        btnyes.setOnClickListener {
            dialogLogOut.dismiss()
            finish()
        }
        btnno.setOnClickListener {
            dialogLogOut.dismiss()
        }
        dialogLogOut.show()
    }

    override fun onClick(v: View) {
         if (v === binding!!.mIVVoice) {
            mCheckVoicePermission()
        } else if (v === binding!!.mIVText) {
            startActivity(Intent(this@HomeActivity, TextActivity::class.java))
        }
    }

    private fun mCheckVoicePermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                startActivity(Intent(this@HomeActivity, VoiceActivity::class.java))
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@HomeActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setRationaleTitle(R.string.rationale_title)
            .setRationaleMessage(R.string.rationale_message_voice)
            .setDeniedTitle("Permission denied")
            .setDeniedMessage(
                "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
            )
            .setGotoSettingButtonText("Settings")
            .setPermissions(Manifest.permission.RECORD_AUDIO)
            .check()
    }
}