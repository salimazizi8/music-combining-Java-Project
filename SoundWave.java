package ca.ubc.ece.cpen221.mp1;

import ca.ubc.ece.cpen221.mp1.utils.ComplexNumber;
import ca.ubc.ece.cpen221.mp1.utils.HasSimilarity;
import javazoom.jl.player.StdPlayer;
import java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;

public class SoundWave implements HasSimilarity<SoundWave> {

    // We are going to treat the number of samples per second of a sound wave
    // as a constant.
    // The best way to refer to this constant is as
    // SoundWave.SAMPLES_PER_SECOND.
    public static final int SAMPLES_PER_SECOND = 44100;

    // some representation fields(attributes) that you could use
    private ArrayList<Double> lchannel = new ArrayList<>();  // ArrayList
    private ArrayList<Double> rchannel = new ArrayList<>(); //  ArrayList
    private int samples = 0;

    /**
     * Create a new SoundWave using the provided left and right channel
     * amplitude values. After the SoundWave is created, changes to the
     * provided arguments will not affect the SoundWave.
     *
     * @param lchannel is not null and represents the left channel.
     * @param rchannel is not null and represents the right channel.
     */
    public SoundWave(double[] lchannel, double[] rchannel) {
        for (double lchannelElement: lchannel) {
            this.lchannel.add(lchannelElement);
        }
        for (double rchannelElement: rchannel) {
            this.rchannel.add(rchannelElement);
        }
        for (double element: lchannel) {
            element = element * 0.5;
        }
    }


    /**
     * Implementing a default constructor
     * that creates an empty wave (all elements are initialized to zero).
     * The initialization naturally implies all the elements are set to zero in this case.
    */
    public SoundWave() {
        this.lchannel = new ArrayList<>();
        this.rchannel = new ArrayList<>();
    }

    /**
     * Create a new sinusoidal sine wave,
     * sampled at a rate of 44,100 samples per second
     *
     * @param freq      the frequency of the sine wave, in Hertz
     * @param phase     the phase of the sine wave, in radians
     * @param amplitude the amplitude of the sine wave, 0 < amplitude <= 1
     * @param duration  the duration of the sine wave, in seconds
     */
    public SoundWave(double freq, double phase, double amplitude, double duration) {
       double delta  = ((2 * Math.PI) / (SAMPLES_PER_SECOND / freq));
       double i = 0.0;
       int j = 0;
       while (j < duration) {
           this.lchannel.add(Math.sin(i + phase) * amplitude);
           this.rchannel.add(Math.sin(i + phase) * amplitude);
           i = i + delta;
           j++;
       }
    }

    /**
     * Obtain the left channel for this wave.
     * Changes to the returned array should not affect this SoundWave.
     *
     * @return an array that represents the left channel for this wave.
     */
    public double[] getLeftChannel() {
        double[] left = new double[this.lchannel.size()];
        for (int i = 0; i < this.lchannel.size(); i++) {
            left [i] = this.lchannel.get(i);
        }
        return left; // change this (changed!)
    }

    /**
     * Obtain the right channel for this wave.
     * Changes to the returned array should not affect this SoundWave.
     *
     * @return an array that represents the right channel for this wave.
     */
    public double[] getRightChannel() {
        double[] right = new double[this.rchannel.size()];
        for (int k = 0; k < this.rchannel.size(); k++) {
            right[k] = this.rchannel.get(k);
        }
        return right;
    }

    /**
     * A simple main method to play an MP3 file. Note that MP3 files should
     * be encoded to use stereo channels and not mono channels for the sound to
     * play out correctly.
     * <p>
     * One should try to get this method to work correctly at the start.
     * </p>
     *
     * @param args are currently ignored but you could be creative.
     */
    /*
    public static void main(String[] args) {
        StdPlayer.open("mp3/anger.mp3");
        SoundWave sw = new SoundWave();
        SoundWave task1 = new SoundWave(200, 15, -0.5, 5);  // an object
       // SoundWave task2 = new SoundWave(15, 10, 15, 20 );
        while (!StdPlayer.isEmpty()) {
            // The music being emitted from the computer is being stored inside the "lchannel":
            double[] lchannel = StdPlayer.getLeftChannel();   // a new Array //
            double[] rchannel = StdPlayer.getRightChannel();  // a new Array
            task1.append(lchannel, rchannel);
        }
        task1.sendToStereoSpeaker();
        StdPlayer.close();
    }
*/
    // SubTask1:
    /**
     * Append a wave to this wave.
     * Appending pieces of amplitude into one huge amplitude wave-form.
     * If the length of one channel is greater than the other, zeros are padded
     * at the end of the shorter channel.
     * @param lchannel1
     * @param rchannel1
     *
     */
  //  private void zero_padding(SoundWave other) {
    public void append(double[] lchannel1, double[] rchannel1) {
        for (double lchannelElement: lchannel1) {
            this.lchannel.add(lchannelElement);
        }
        for (double rchannelElement: rchannel1) {
            this.rchannel.add(rchannelElement);
        }
        int difference = 0;
        if (this.lchannel.size() > this.rchannel.size()) {
            difference = this.lchannel.size() - this.rchannel.size();
            for (int i = 0; i < difference; i++) {
                this.rchannel.add(0.0);
            }
        } else if (this.lchannel.size() < this.rchannel.size()) {
            difference = this.rchannel.size() - this.lchannel.size();
            for (int i = 0; i < difference; i++) {
                this.lchannel.add(0.0);
            }
        }
    }

    // SubTask2 (Appending Waves):
    /**
     * Append a wave to this wave.
     *
     * @param other the wave to append.
     */
    public void append(SoundWave other) {
        for (double lchannelElement: other.lchannel) {
            this.lchannel.add(lchannelElement);
            /* appending all the elements in the "other.sound_wave()"
            into the "this.sound_wave()" */
        }
        for (double rchannelElement: other.rchannel) {
            this.rchannel.add(rchannelElement);
        }
    }

    // Subtask 3:
    /**
     * Create a new wave by adding another wave to this wave.
     * (You should also write clear specifications for this method.)!!!
     * @param other other is the object whose left and right channels
     *              are added into the original wave's channels.
     * @return a new sound wave superimposed of the two waves
     * @modify This method modifies the input or the object called on this method,
     * if either of the object's channels are longer.
     */
    public SoundWave add(SoundWave other) {
        SoundWave newWave1 = this;    // Copying the current object into some other object
        SoundWave newWave2 = other;

        SoundWave added_wave = new SoundWave(); /* the object being returned at the end */
        int diff = Math.abs(newWave1.lchannel.size() - newWave2.lchannel.size());
        if (newWave1.lchannel.size() > newWave2.lchannel.size()) {
            for (int k = 0; k < diff; k++) {
                newWave2.lchannel.add(0.0);
                newWave2.rchannel.add(0.0);
            }
        } else {
            for (int k = 0; k < diff; k++) {
                newWave1.lchannel.add(0.0);
                newWave1.rchannel.add(0.0);
            }
        }

        for (int k = 0; k < Math.max(newWave1.lchannel.size(), newWave2.lchannel.size()); k++) {
            if ((newWave1.lchannel.get(k) + newWave2.lchannel.get(k)) > 1.0) {
                added_wave.lchannel.add(1.0);
            } else if ((newWave1.lchannel.get(k) + newWave2.lchannel.get(k)) < -1.0) {
                added_wave.lchannel.add(-1.0);
            } else {
                added_wave.lchannel.add(newWave1.lchannel.get(k) + newWave2.lchannel.get(k));
            }

            /* superimposing for right channel, ensuring amplitude does not exceed [-1,1]: */
            if ((newWave1.rchannel.get(k) + newWave2.rchannel.get(k)) > 1.0) {
                added_wave.rchannel.add(1.0);
            } else if ((newWave1.rchannel.get(k) + newWave2.rchannel.get(k)) < -1.0) {
                added_wave.rchannel.add(-1.0);
            } else {
                added_wave.rchannel.add(newWave1.rchannel.get(k) + newWave2.rchannel.get(k));
            }
        }

        return added_wave;
    }

    /**
     * Create a new wave by adding an echo to this wave.
     * Amplitudes in left channel and right channel are clipped to be [-1.0,1.0] before returning.
     * @param delta > 0. delta is the lag between this wave and the echo wave.
     * @param alpha > 0. alpha is the damping factor applied to the echo wave.
     * @return a new sound wave with an echo.
     */
    public SoundWave addEcho(int delta, double alpha) {
        SoundWave modified_wave = new SoundWave();
        SoundWave echoed_wave = new SoundWave();
        SoundWave echoed_wave_finalized = new SoundWave();
        SoundWave delayed_and_reduced = new SoundWave();

        for (int i = 0; i < delta; i++) {
            modified_wave.lchannel.add(0.0);
            modified_wave.rchannel.add(0.0);
        }
        for (double lchannelElement: this.lchannel) {
            modified_wave.lchannel.add(lchannelElement);
        }
        for (double rchannelElement: this.rchannel) {
            modified_wave.rchannel.add(rchannelElement);
        }

        // Multiplying the left channel of the delayed signal by Alpha:
        for (double modifiedLeftElement: modified_wave.lchannel) {
            delayed_and_reduced.lchannel.add(modifiedLeftElement * alpha);
        }
        // Multiplying the delayed the right channel of the delayed signal by Alpha:
        for (double modifiedRightElement: modified_wave.rchannel) {
            delayed_and_reduced.rchannel.add(modifiedRightElement * alpha);
        }

        echoed_wave = this.add(delayed_and_reduced);
        // Ensuring the wave amplitudes do not exceed 1 or -1 :
        for (double echoedLeftElement: echoed_wave.lchannel) {
            if (echoedLeftElement > 1.0) {
                echoed_wave_finalized.lchannel.add(1.0);
            } else if (echoedLeftElement < -1.0) {
                echoed_wave_finalized.lchannel.add(-1.0);
            } else {
                echoed_wave_finalized.lchannel.add(echoedLeftElement);
            }
        }

        for (double echoedRightElement: echoed_wave.rchannel) {
            if (echoedRightElement > 1.0) {
                echoed_wave_finalized.rchannel.add(1.0);
            } else if (echoedRightElement < -1.0) {
                echoed_wave_finalized.rchannel.add(-1.0);
            } else {
                echoed_wave_finalized.rchannel.add(echoedRightElement);
            }
        }
        return echoed_wave_finalized;
    }

    /**
     * Scale the amplitude of this wave by a scaling factor.
     * After scaling, the amplitude values are clipped to remain
     * between -1 and +1.
     *
     * @param scalingFactor is a value > 0.
     */
    public void scale(double scalingFactor) {
        ArrayList<Double> left_reduced = new ArrayList<>();
        ArrayList<Double> right_reduced = new ArrayList<>();
        for (double lchannelElement: this.lchannel) {
            left_reduced.add(lchannelElement * scalingFactor);
        }
        for (double rchannelElement: this.rchannel) {
            right_reduced.add(rchannelElement * scalingFactor);
        }
        this.lchannel.clear();
        this.rchannel.clear();
        for (double leftElementReduced: left_reduced) {
            if (leftElementReduced > 1.0) {
                this.lchannel.add(1.0);
            } else if (leftElementReduced < -1.0) {
                this.lchannel.add(-1.0);
            } else {
                this.lchannel.add(leftElementReduced);
            }
        }
        for (double rightElementReduced: right_reduced) {
            if (rightElementReduced > 1.0) {
                this.rchannel.add(1.0);
            } else if (rightElementReduced < -1.0) {
                this.rchannel.add(-1.0);
            } else {
                this.rchannel.add(rightElementReduced);
            }
        }
    }

    /**
     * Return a new sound wave that is obtained by applying a high-pass filter to
     * this wave.
     *
     * @param dt >= 0. dt is the time interval used in the filtering process.
     * @param rc > 0. RC is the time constant for the high-pass filter.
     * @return
     */
    public SoundWave highPassFilter(int dt, double rc) {
        SoundWave filtered_wave = new SoundWave();
        double a = rc / (rc + dt);
        double past_filtered_left;
        double past_unfiltered_left;
        filtered_wave.lchannel.add(this.lchannel.get(0));
        for (int i = 1; i < this.lchannel.size(); ++i) {
            past_filtered_left = filtered_wave.lchannel.get(--i);
            ++i;
            past_unfiltered_left = this.lchannel.get(--i);
            ++i;
            filtered_wave.lchannel.add(a * past_filtered_left
                                      + a * (this.lchannel.get(i) - past_unfiltered_left));
        }

        double past_filtered_right;  // previous filtered value for y
        double past_unfiltered_right; // previous value for x (unfiltered value for x)
        filtered_wave.rchannel.add(this.rchannel.get(0));
        for (int k = 1; k < this.rchannel.size(); ++k) {
            past_filtered_right = filtered_wave.rchannel.get(--k);
            ++k;
            past_unfiltered_right = this.rchannel.get(--k);
            ++k;
            filtered_wave.rchannel.add(a * past_filtered_right
                                   + a * (this.rchannel.get(k) - past_unfiltered_right));
        }
        return filtered_wave;
    }

    /**
     * Return the frequency of the component with the greatest amplitude
     * contribution to this wave. This component is obtained by applying the
     * Discrete Fourier Transform to this wave.
     *
     * @return the frequency of the wave component of highest amplitude.
     * If more than one frequency has the same amplitude contribution then
     * return the higher frequency.
     */
    public double highAmplitudeFreqComponent() {
        int maxLeft = getMaxForChannel(this.lchannel);
        int maxRight = getMaxForChannel(this.rchannel);
        double N = this.lchannel.size();
        double result = Math.max(maxLeft, maxRight) * N / SAMPLES_PER_SECOND;
        return result; // change this (done)
    }

    /**
     * Helper Function
     * @param channel this the channel on which a Discrete Fourier Transform operates.
     * @return frequency component of the highest amplitude.
     */
    private int getMaxForChannel(ArrayList<Double> channel) {
        ArrayList<ComplexNumber> list_left = new ArrayList<>();

        for (int k = 0; k <= channel.size() - 1; k++) {
            ComplexNumber sum = new ComplexNumber(0, 0);
            for (int n = 0; n <= channel.size() - 1; n++) {
                ComplexNumber val = new ComplexNumber(channel.get(n)
                         * Math.cos(2 * Math.PI * k * n / (channel.size() * SAMPLES_PER_SECOND)),
                        channel.get(n)
                         * Math.sin(2 * Math.PI * k * n / (channel.size() * SAMPLES_PER_SECOND)));
                sum.add_complexes(val);
            }
            list_left.add(sum);
        }

        double max_amplitude = 0.0;
        int fourier_coefficient = 0;
        double mag;
        for (int index = 0; index < list_left.size(); index++) {
            mag = ComplexNumber.magnitude(list_left.get(index));
            if (mag >= max_amplitude) {
                max_amplitude = mag;
                fourier_coefficient = index;
            }
        }
        return fourier_coefficient;
    }

//***** TASK 2 ******

    /**
     * Determine if this wave fully contains the other sound wave as a pattern.
     *
     * @param other is the wave to search for in this wave.
     * @return true if the other wave is contained in this after amplitude scaling,
     * and false if the other wave is not contained in this with any
     * possible amplitude scaling.
     */
    public boolean contains(SoundWave other) {
        double beta = 0.0;
        if (this.lchannel.size() < other.lchannel.size()) {
            return false;
        }
        double lchannelDivision = this.lchannel.get(0) / other.lchannel.get(0);
        double rchannelDivision = this.rchannel.get(0) / other.rchannel.get(0);
        if (lchannelDivision == rchannelDivision) {
            beta = lchannelDivision;
        } else {
            return false;
        }
        boolean left_channel = checking_channels(this.lchannel, other.lchannel, beta);
        boolean right_channel = checking_channels(this.rchannel, other.rchannel, beta);

        return (left_channel == right_channel);
    }

    /**
     * Helper method.
     * Determine for every channel of a SoundWave if its elements are
     * scaled version of another SoundWave.
     *
     * @param channel1 the first SoundWave whose channels are compared.
     * @param other_channel the second SoundWave which is checked whether it is
     * contained in the first SoundWave.
     * @param beta the scaling factor
     * @return true if the left channel for first SoundWave is a scaled version
     * of second SoundWave and it its right channel is a scaled version of the
     * other SoundWave's right channel.
     */
    private boolean checking_channels(ArrayList<Double> channel1,
                                      ArrayList<Double> other_channel,
                                      double beta) {
        int count = 0;
        for (int i = 0; i < other_channel.size(); i++) {
            if (channel1.get(i) / other_channel.get(i) == beta) {
                count++;
            }
        }
        return (count == other_channel.size());
    }

    /**
     * Determine the similarity between this wave and another wave.
     * The similarity metric, gamma, is the sum of squares of
     * instantaneous differences.
     *
     * @param other is not null. other is assumed to be wave2
     * @return the similarity between this wave and other.
     */
    public double similarity(SoundWave other) {
        if (this.lchannel.size() == 0 || this.rchannel.size() == 0 ||
            other.lchannel.size() == 0 || other.rchannel.size() == 0) {
            return 0;
        }
        this.zero_padding(other);
        double beta_w1_w2 = beta_calculator_w1_w2(this, other);
        double beta_w2_w1 = beta_calculator_w2_w1(this, other);
        double gama_w1_w2 = gama_calculate_w1_w2(this, other, beta_w1_w2);
        double gama_w2_w1 = gama_calculate_w2_w1(this, other, beta_w2_w1);

        return ((gama_w1_w2 + gama_w2_w1) / 2);
    }

    /**
     * Helper method.
     * Calculate gama(w1,w2) based on the similarity equation on the Notion WebPage.
     */
    private static double gama_calculate_w1_w2(SoundWave w1, SoundWave w2, double beta) {
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;
        double gama = 0.0;
        for (int i = 0; i < w2.lchannel.size(); i++) {
            a = a + (Math.pow(w2.rchannel.get(i), 2) + Math.pow(w2.lchannel.get(i), 2));
        }
        for (int k = 0; k < w2.lchannel.size(); k++) {
            b = b + (w1.rchannel.get(k) * w2.rchannel.get(k)
                    + w1.lchannel.get(k) * w2.lchannel.get(k));
        }
        for (int q = 0; q < w2.lchannel.size(); q++) {
            c = c + (Math.pow(w1.rchannel.get(q), 2) + Math.pow(w1.lchannel.get(q), 2));
        }
        gama = 1 / (1 + Math.pow(beta, 2) * a - 2 * beta * b + c);
        return gama;
    }

    /**
     * Helper method.
     * Calculate gama(w2,w1) based on the similarity equation.
     */
    private static double gama_calculate_w2_w1(SoundWave w1, SoundWave w2, double beta) {
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;
        double gama = 0.0;
        for (int i = 0; i < w1.lchannel.size(); i++) {
            a = a + (Math.pow(w1.rchannel.get(i), 2) + Math.pow(w1.lchannel.get(i), 2));
        }
        for (int k = 0; k < w1.lchannel.size(); k++) {
            b = b + (w2.rchannel.get(k) * w1.rchannel.get(k)
                   + w2.lchannel.get(k) * w1.lchannel.get(k));
        }
        for (int v = 0; v < w1.lchannel.size(); v++) {
            c = c + (Math.pow(w2.rchannel.get(v), 2) + Math.pow(w2.lchannel.get(v), 2));
        }
        gama = 1 / (1 + Math.pow(beta, 2) * a - 2 * beta * b + c);
        return gama;
    }

    /**
     * Helper method.
     * Calculate beta for the case which we find Gama(w1,w2).
     * @return the computed beta value, or 10^-7 if the computed beta is smaller
     * than or equal to zero.
     *
     */
    public static double beta_calculator_w1_w2(SoundWave w1, SoundWave w2) {
        double numerator_sum = 0.0;
        double denumerator_sum = 0.0;
        for (int i = 0; i < w1.lchannel.size(); i++) {
            numerator_sum = numerator_sum + (w1.lchannel.get(i) * w2.lchannel.get(i)
                    + w1.rchannel.get(i) * w2.rchannel.get(i));

            denumerator_sum = denumerator_sum + (w2.lchannel.get(i)
                    * w2.lchannel.get(i) + w2.rchannel.get(i) * w2.rchannel.get(i));
        }
        // To account for the edge case that beta <= 0 :
        if ((numerator_sum / denumerator_sum) <= 0.0) {
            return Math.pow(10, -7);
        } else {
            return numerator_sum / denumerator_sum;
        }
    }

    /**
     * Helper method.
     * Calculate beta for the case which we find Gama(w2,w1).
     */
    private double beta_calculator_w2_w1(SoundWave w1, SoundWave w2) {
        double numerator_sum = 0.0;
        double denumerator_sum = 0.0;
        for (int i = 0; i < w1.lchannel.size(); i++) {
            numerator_sum = numerator_sum + (w2.lchannel.get(i)
                    * w1.lchannel.get(i) + w2.rchannel.get(i) * w1.rchannel.get(i));

            denumerator_sum = denumerator_sum + (w1.lchannel.get(i)
                    * w1.lchannel.get(i) + w1.rchannel.get(i) * w1.rchannel.get(i));
        }
        if ((numerator_sum / denumerator_sum) <= 0.0) {
            return Math.pow(10, -7);
        } else {
            return numerator_sum / denumerator_sum;
        }
    }

    /**
     * Helper method.
     * Append zeros at the end of whichever padding for the left
     * and right of the shorter SoundWave objects.
     */
    private void zero_padding(SoundWave other) {
        int difference = 0;
        if (this.lchannel.size() > other.lchannel.size()) {
            difference = this.lchannel.size() - other.lchannel.size();
            for (int i = 0; i < difference; i++) {
                other.lchannel.add(0.0);
                other.rchannel.add(0.0);
            }
        } else if (this.lchannel.size() < other.lchannel.size()) {
            difference = other.lchannel.size() - this.lchannel.size();
            for (int i = 0; i < difference; i++) {
                this.lchannel.add(0.0);
                this.rchannel.add(0.0);
            }
        }
    }

    /**
     * Play this wave on the standard stereo device.
     */
    public void sendToStereoSpeaker() {
        // You may not need to change this...
        /**
         * Play this wave on the standard stereo device.
         */
        double[] lchannel1 = this.lchannel.stream().mapToDouble(x -> x.doubleValue()).toArray();
        double[] rchannel1 = this.rchannel.stream().mapToDouble(x -> x.doubleValue()).toArray();
        StdPlayer.playWave(lchannel1, rchannel1);
    }

}
