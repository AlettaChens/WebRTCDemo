package com.example.webrtcdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.webrtc.Camera1Enumerator;
import org.webrtc.EglBase;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.SurfaceTextureHelper;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;


/**
 * 描述：WebRTC 使用本地摄像头
 * <p>
 * <p>
 * 主要步骤如下:
 * 1.创建PeerConnectionFactory
 * 2.创建并启动VideoCapturer
 * 3.用PeerConnectionFactory创建VideoSource
 * 4.用PeerConnectionFactory和VideoSource创建VideoTrack
 * 5.初始化视频控件SurfaceViewRenderer
 * 6.将VideoTrack展示到SurfaceViewRenderer中
 */
public class WebRTCLocalCamera01Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_camera01);

        findViewById(R.id.btn_back).setOnClickListener(v -> onBackPressed());

        EglBase.Context eglBaseContext = EglBase.create().getEglBaseContext();
        SurfaceTextureHelper surfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", eglBaseContext);
        // create PeerConnectionFactory
        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(this).createInitializationOptions());
        PeerConnectionFactory peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory();
        // create VideoCapturer
        VideoCapturer videoCapturer = createCameraCapturer();
        VideoSource videoSource = peerConnectionFactory.createVideoSource(videoCapturer.isScreencast());
        videoCapturer.initialize(surfaceTextureHelper, getApplicationContext(), videoSource.getCapturerObserver());
        videoCapturer.startCapture(480, 640, 30);
        //初始化视频控件SurfaceViewRenderer
        SurfaceViewRenderer localView = findViewById(R.id.localView);
        localView.setMirror(false);
        localView.init(eglBaseContext, null);
        // create VideoTrack
        VideoTrack videoTrack = peerConnectionFactory.createVideoTrack("101", videoSource);
        // display in localView
        videoTrack.addSink(localView);
    }

    private VideoCapturer createCameraCapturer() {
        Camera1Enumerator enumerator = new Camera1Enumerator(false);
        final String[] deviceNames = enumerator.getDeviceNames();
        for (String deviceName : deviceNames) {
            if (enumerator.isBackFacing(deviceName)) {
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }
        return null;
    }
}
