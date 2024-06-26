package com.capstone.capstonedesign.service.cctv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class FrameGrabber {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public byte[] frameGrab() throws IOException {
        VideoCapture capture = new VideoCapture();

        if (!capture.open("rtsp://172.16.189.76:8888/h264.sdp")) {
            throw new IOException("Error: Couldn't open RTSP stream.");
        }

        Mat frame = new Mat();

        if (capture.read(frame)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".png", frame, matOfByte);
            baos.write(matOfByte.toArray());
            capture.release();
            return baos.toByteArray();
        } else {
            capture.release();
            throw new IOException("Error: Couldn't read frame from RTSP stream.");
        }
    }
}