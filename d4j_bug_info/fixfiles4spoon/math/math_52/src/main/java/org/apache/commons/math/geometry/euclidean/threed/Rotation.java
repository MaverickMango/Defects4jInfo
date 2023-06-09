package org.apache.commons.math.geometry.euclidean.threed;


public class Rotation implements java.io.Serializable {
    public static final org.apache.commons.math.geometry.euclidean.threed.Rotation IDENTITY = new org.apache.commons.math.geometry.euclidean.threed.Rotation(1.0, 0.0, 0.0, 0.0, false);

    private static final long serialVersionUID = -2153622329907944313L;

    private final double q0;

    private final double q1;

    private final double q2;

    private final double q3;

    public Rotation(double q0, double q1, double q2, double q3, boolean needsNormalization) {
        if (needsNormalization) {
            double inv = 1.0 / (org.apache.commons.math.util.FastMath.sqrt(((((q0 * q0) + (q1 * q1)) + (q2 * q2)) + (q3 * q3))));
            q0 *= inv;
            q1 *= inv;
            q2 *= inv;
            q3 *= inv;
        }
        this.q0 = q0;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
    }

    public Rotation(org.apache.commons.math.geometry.euclidean.threed.Vector3D axis, double angle) {
        double norm = axis.getNorm();
        if (norm == 0) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_AXIS);
        }
        double halfAngle = (-0.5) * angle;
        double coeff = (org.apache.commons.math.util.FastMath.sin(halfAngle)) / norm;
        q0 = org.apache.commons.math.util.FastMath.cos(halfAngle);
        q1 = coeff * (axis.getX());
        q2 = coeff * (axis.getY());
        q3 = coeff * (axis.getZ());
    }

    public Rotation(double[][] m, double threshold) throws org.apache.commons.math.geometry.euclidean.threed.NotARotationMatrixException {
        if (((((m.length) != 3) || ((m[0].length) != 3)) || ((m[1].length) != 3)) || ((m[2].length) != 3)) {
            throw new org.apache.commons.math.geometry.euclidean.threed.NotARotationMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.ROTATION_MATRIX_DIMENSIONS, m.length, m[0].length);
        }
        double[][] ort = orthogonalizeMatrix(m, threshold);
        double det = (((ort[0][0]) * (((ort[1][1]) * (ort[2][2])) - ((ort[2][1]) * (ort[1][2])))) - ((ort[1][0]) * (((ort[0][1]) * (ort[2][2])) - ((ort[2][1]) * (ort[0][2]))))) + ((ort[2][0]) * (((ort[0][1]) * (ort[1][2])) - ((ort[1][1]) * (ort[0][2]))));
        if (det < 0.0) {
            throw new org.apache.commons.math.geometry.euclidean.threed.NotARotationMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.CLOSEST_ORTHOGONAL_MATRIX_HAS_NEGATIVE_DETERMINANT, det);
        }
        double s = ((ort[0][0]) + (ort[1][1])) + (ort[2][2]);
        if (s > (-0.19)) {
            q0 = 0.5 * (org.apache.commons.math.util.FastMath.sqrt((s + 1.0)));
            double inv = 0.25 / (q0);
            q1 = inv * ((ort[1][2]) - (ort[2][1]));
            q2 = inv * ((ort[2][0]) - (ort[0][2]));
            q3 = inv * ((ort[0][1]) - (ort[1][0]));
        }else {
            s = ((ort[0][0]) - (ort[1][1])) - (ort[2][2]);
            if (s > (-0.19)) {
                q1 = 0.5 * (org.apache.commons.math.util.FastMath.sqrt((s + 1.0)));
                double inv = 0.25 / (q1);
                q0 = inv * ((ort[1][2]) - (ort[2][1]));
                q2 = inv * ((ort[0][1]) + (ort[1][0]));
                q3 = inv * ((ort[0][2]) + (ort[2][0]));
            }else {
                s = ((ort[1][1]) - (ort[0][0])) - (ort[2][2]);
                if (s > (-0.19)) {
                    q2 = 0.5 * (org.apache.commons.math.util.FastMath.sqrt((s + 1.0)));
                    double inv = 0.25 / (q2);
                    q0 = inv * ((ort[2][0]) - (ort[0][2]));
                    q1 = inv * ((ort[0][1]) + (ort[1][0]));
                    q3 = inv * ((ort[2][1]) + (ort[1][2]));
                }else {
                    s = ((ort[2][2]) - (ort[0][0])) - (ort[1][1]);
                    q3 = 0.5 * (org.apache.commons.math.util.FastMath.sqrt((s + 1.0)));
                    double inv = 0.25 / (q3);
                    q0 = inv * ((ort[0][1]) - (ort[1][0]));
                    q1 = inv * ((ort[0][2]) + (ort[2][0]));
                    q2 = inv * ((ort[2][1]) + (ort[1][2]));
                }
            }
        }
    }

    public Rotation(org.apache.commons.math.geometry.euclidean.threed.Vector3D u1, org.apache.commons.math.geometry.euclidean.threed.Vector3D u2, org.apache.commons.math.geometry.euclidean.threed.Vector3D v1, org.apache.commons.math.geometry.euclidean.threed.Vector3D v2) {
        double u1u1 = u1.getNormSq();
        double u2u2 = u2.getNormSq();
        double v1v1 = v1.getNormSq();
        double v2v2 = v2.getNormSq();
        if ((((u1u1 == 0) || (u2u2 == 0)) || (v1v1 == 0)) || (v2v2 == 0)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR);
        }
        v1 = new org.apache.commons.math.geometry.euclidean.threed.Vector3D(org.apache.commons.math.util.FastMath.sqrt((u1u1 / v1v1)), v1);
        double u1u2 = u1.dotProduct(u2);
        double v1v2 = v1.dotProduct(v2);
        double coeffU = u1u2 / u1u1;
        double coeffV = v1v2 / u1u1;
        double beta = org.apache.commons.math.util.FastMath.sqrt(((u2u2 - (u1u2 * coeffU)) / (v2v2 - (v1v2 * coeffV))));
        double alpha = coeffU - (beta * coeffV);
        v2 = new org.apache.commons.math.geometry.euclidean.threed.Vector3D(alpha, v1, beta, v2);
        org.apache.commons.math.geometry.euclidean.threed.Vector3D uRef = u1;
        org.apache.commons.math.geometry.euclidean.threed.Vector3D vRef = v1;
        org.apache.commons.math.geometry.euclidean.threed.Vector3D v1Su1 = v1.subtract(u1);
        org.apache.commons.math.geometry.euclidean.threed.Vector3D v2Su2 = v2.subtract(u2);
        org.apache.commons.math.geometry.euclidean.threed.Vector3D k = v1Su1.crossProduct(v2Su2);
        org.apache.commons.math.geometry.euclidean.threed.Vector3D u3 = u1.crossProduct(u2);
        double c = k.dotProduct(u3);
        final double inPlaneThreshold = 0.001;
        if (c <= ((inPlaneThreshold * (k.getNorm())) * (u3.getNorm()))) {
            org.apache.commons.math.geometry.euclidean.threed.Vector3D v3 = org.apache.commons.math.geometry.euclidean.threed.Vector3D.crossProduct(v1, v2);
            org.apache.commons.math.geometry.euclidean.threed.Vector3D v3Su3 = v3.subtract(u3);
            k = v1Su1.crossProduct(v3Su3);
            org.apache.commons.math.geometry.euclidean.threed.Vector3D u2Prime = u1.crossProduct(u3);
            c = k.dotProduct(u2Prime);
            if (c <= ((inPlaneThreshold * (k.getNorm())) * (u2Prime.getNorm()))) {
                k = v2Su2.crossProduct(v3Su3);
                c = k.dotProduct(u2.crossProduct(u3));
                if (c <= 0) {
                    q0 = 1.0;
                    q1 = 0.0;
                    q2 = 0.0;
                    q3 = 0.0;
                    return;
                }
                uRef = u2;
                vRef = v2;
            }
        }
        c = org.apache.commons.math.util.FastMath.sqrt(c);
        double inv = 1.0 / (c + c);
        q1 = inv * (k.getX());
        q2 = inv * (k.getY());
        q3 = inv * (k.getZ());
        k = new org.apache.commons.math.geometry.euclidean.threed.Vector3D((((uRef.getY()) * (q3)) - ((uRef.getZ()) * (q2))), (((uRef.getZ()) * (q1)) - ((uRef.getX()) * (q3))), (((uRef.getX()) * (q2)) - ((uRef.getY()) * (q1))));
        q0 = (vRef.dotProduct(k)) / (2 * (k.getNormSq()));
    }

    public Rotation(org.apache.commons.math.geometry.euclidean.threed.Vector3D u, org.apache.commons.math.geometry.euclidean.threed.Vector3D v) {
        double normProduct = (u.getNorm()) * (v.getNorm());
        if (normProduct == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR);
        }
        double dot = u.dotProduct(v);
        if (dot < ((2.0E-15 - 1.0) * normProduct)) {
            org.apache.commons.math.geometry.euclidean.threed.Vector3D w = u.orthogonal();
            q0 = 0.0;
            q1 = -(w.getX());
            q2 = -(w.getY());
            q3 = -(w.getZ());
        }else {
            q0 = org.apache.commons.math.util.FastMath.sqrt((0.5 * (1.0 + (dot / normProduct))));
            double coeff = 1.0 / ((2.0 * (q0)) * normProduct);
            org.apache.commons.math.geometry.euclidean.threed.Vector3D q = v.crossProduct(u);
            q1 = coeff * (q.getX());
            q2 = coeff * (q.getY());
            q3 = coeff * (q.getZ());
        }
    }

    public Rotation(org.apache.commons.math.geometry.euclidean.threed.RotationOrder order, double alpha1, double alpha2, double alpha3) {
        org.apache.commons.math.geometry.euclidean.threed.Rotation r1 = new org.apache.commons.math.geometry.euclidean.threed.Rotation(order.getA1(), alpha1);
        org.apache.commons.math.geometry.euclidean.threed.Rotation r2 = new org.apache.commons.math.geometry.euclidean.threed.Rotation(order.getA2(), alpha2);
        org.apache.commons.math.geometry.euclidean.threed.Rotation r3 = new org.apache.commons.math.geometry.euclidean.threed.Rotation(order.getA3(), alpha3);
        org.apache.commons.math.geometry.euclidean.threed.Rotation composed = r1.applyTo(r2.applyTo(r3));
        q0 = composed.q0;
        q1 = composed.q1;
        q2 = composed.q2;
        q3 = composed.q3;
    }

    public org.apache.commons.math.geometry.euclidean.threed.Rotation revert() {
        return new org.apache.commons.math.geometry.euclidean.threed.Rotation((-(q0)), q1, q2, q3, false);
    }

    public double getQ0() {
        return q0;
    }

    public double getQ1() {
        return q1;
    }

    public double getQ2() {
        return q2;
    }

    public double getQ3() {
        return q3;
    }

    public org.apache.commons.math.geometry.euclidean.threed.Vector3D getAxis() {
        double squaredSine = (((q1) * (q1)) + ((q2) * (q2))) + ((q3) * (q3));
        if (squaredSine == 0) {
            return new org.apache.commons.math.geometry.euclidean.threed.Vector3D(1, 0, 0);
        }else
            if ((q0) < 0) {
                double inverse = 1 / (org.apache.commons.math.util.FastMath.sqrt(squaredSine));
                return new org.apache.commons.math.geometry.euclidean.threed.Vector3D(((q1) * inverse), ((q2) * inverse), ((q3) * inverse));
            }

        double inverse = (-1) / (org.apache.commons.math.util.FastMath.sqrt(squaredSine));
        return new org.apache.commons.math.geometry.euclidean.threed.Vector3D(((q1) * inverse), ((q2) * inverse), ((q3) * inverse));
    }

    public double getAngle() {
        if (((q0) < (-0.1)) || ((q0) > 0.1)) {
            return 2 * (org.apache.commons.math.util.FastMath.asin(org.apache.commons.math.util.FastMath.sqrt(((((q1) * (q1)) + ((q2) * (q2))) + ((q3) * (q3))))));
        }else
            if ((q0) < 0) {
                return 2 * (org.apache.commons.math.util.FastMath.acos((-(q0))));
            }

        return 2 * (org.apache.commons.math.util.FastMath.acos(q0));
    }

    public double[] getAngles(org.apache.commons.math.geometry.euclidean.threed.RotationOrder order) throws org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException {
        if (order == (RotationOrder.XYZ)) {
            org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_K);
            org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_I);
            if (((v2.getZ()) < (-0.9999999999)) || ((v2.getZ()) > 0.9999999999)) {
                throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(true);
            }
            return new double[]{ org.apache.commons.math.util.FastMath.atan2((-(v1.getY())), v1.getZ()), org.apache.commons.math.util.FastMath.asin(v2.getZ()), org.apache.commons.math.util.FastMath.atan2((-(v2.getY())), v2.getX()) };
        }else
            if (order == (RotationOrder.XZY)) {
                org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_J);
                org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_I);
                if (((v2.getY()) < (-0.9999999999)) || ((v2.getY()) > 0.9999999999)) {
                    throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(true);
                }
                return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getZ(), v1.getY()), -(org.apache.commons.math.util.FastMath.asin(v2.getY())), org.apache.commons.math.util.FastMath.atan2(v2.getZ(), v2.getX()) };
            }else
                if (order == (RotationOrder.YXZ)) {
                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_K);
                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_J);
                    if (((v2.getZ()) < (-0.9999999999)) || ((v2.getZ()) > 0.9999999999)) {
                        throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(true);
                    }
                    return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getX(), v1.getZ()), -(org.apache.commons.math.util.FastMath.asin(v2.getZ())), org.apache.commons.math.util.FastMath.atan2(v2.getX(), v2.getY()) };
                }else
                    if (order == (RotationOrder.YZX)) {
                        org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_I);
                        org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_J);
                        if (((v2.getX()) < (-0.9999999999)) || ((v2.getX()) > 0.9999999999)) {
                            throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(true);
                        }
                        return new double[]{ org.apache.commons.math.util.FastMath.atan2((-(v1.getZ())), v1.getX()), org.apache.commons.math.util.FastMath.asin(v2.getX()), org.apache.commons.math.util.FastMath.atan2((-(v2.getZ())), v2.getY()) };
                    }else
                        if (order == (RotationOrder.ZXY)) {
                            org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_J);
                            org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_K);
                            if (((v2.getY()) < (-0.9999999999)) || ((v2.getY()) > 0.9999999999)) {
                                throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(true);
                            }
                            return new double[]{ org.apache.commons.math.util.FastMath.atan2((-(v1.getX())), v1.getY()), org.apache.commons.math.util.FastMath.asin(v2.getY()), org.apache.commons.math.util.FastMath.atan2((-(v2.getX())), v2.getZ()) };
                        }else
                            if (order == (RotationOrder.ZYX)) {
                                org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_I);
                                org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_K);
                                if (((v2.getX()) < (-0.9999999999)) || ((v2.getX()) > 0.9999999999)) {
                                    throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(true);
                                }
                                return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getY(), v1.getX()), -(org.apache.commons.math.util.FastMath.asin(v2.getX())), org.apache.commons.math.util.FastMath.atan2(v2.getY(), v2.getZ()) };
                            }else
                                if (order == (RotationOrder.XYX)) {
                                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_I);
                                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_I);
                                    if (((v2.getX()) < (-0.9999999999)) || ((v2.getX()) > 0.9999999999)) {
                                        throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(false);
                                    }
                                    return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getY(), (-(v1.getZ()))), org.apache.commons.math.util.FastMath.acos(v2.getX()), org.apache.commons.math.util.FastMath.atan2(v2.getY(), v2.getZ()) };
                                }else
                                    if (order == (RotationOrder.XZX)) {
                                        org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_I);
                                        org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_I);
                                        if (((v2.getX()) < (-0.9999999999)) || ((v2.getX()) > 0.9999999999)) {
                                            throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(false);
                                        }
                                        return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getZ(), v1.getY()), org.apache.commons.math.util.FastMath.acos(v2.getX()), org.apache.commons.math.util.FastMath.atan2(v2.getZ(), (-(v2.getY()))) };
                                    }else
                                        if (order == (RotationOrder.YXY)) {
                                            org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_J);
                                            org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_J);
                                            if (((v2.getY()) < (-0.9999999999)) || ((v2.getY()) > 0.9999999999)) {
                                                throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(false);
                                            }
                                            return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getX(), v1.getZ()), org.apache.commons.math.util.FastMath.acos(v2.getY()), org.apache.commons.math.util.FastMath.atan2(v2.getX(), (-(v2.getZ()))) };
                                        }else
                                            if (order == (RotationOrder.YZY)) {
                                                org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_J);
                                                org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_J);
                                                if (((v2.getY()) < (-0.9999999999)) || ((v2.getY()) > 0.9999999999)) {
                                                    throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(false);
                                                }
                                                return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getZ(), (-(v1.getX()))), org.apache.commons.math.util.FastMath.acos(v2.getY()), org.apache.commons.math.util.FastMath.atan2(v2.getZ(), v2.getX()) };
                                            }else
                                                if (order == (RotationOrder.ZXZ)) {
                                                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_K);
                                                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_K);
                                                    if (((v2.getZ()) < (-0.9999999999)) || ((v2.getZ()) > 0.9999999999)) {
                                                        throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(false);
                                                    }
                                                    return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getX(), (-(v1.getY()))), org.apache.commons.math.util.FastMath.acos(v2.getZ()), org.apache.commons.math.util.FastMath.atan2(v2.getX(), v2.getY()) };
                                                }else {
                                                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v1 = applyTo(Vector3D.PLUS_K);
                                                    org.apache.commons.math.geometry.euclidean.threed.Vector3D v2 = applyInverseTo(Vector3D.PLUS_K);
                                                    if (((v2.getZ()) < (-0.9999999999)) || ((v2.getZ()) > 0.9999999999)) {
                                                        throw new org.apache.commons.math.geometry.euclidean.threed.CardanEulerSingularityException(false);
                                                    }
                                                    return new double[]{ org.apache.commons.math.util.FastMath.atan2(v1.getY(), v1.getX()), org.apache.commons.math.util.FastMath.acos(v2.getZ()), org.apache.commons.math.util.FastMath.atan2(v2.getY(), (-(v2.getX()))) };
                                                }










    }

    public double[][] getMatrix() {
        double q0q0 = (q0) * (q0);
        double q0q1 = (q0) * (q1);
        double q0q2 = (q0) * (q2);
        double q0q3 = (q0) * (q3);
        double q1q1 = (q1) * (q1);
        double q1q2 = (q1) * (q2);
        double q1q3 = (q1) * (q3);
        double q2q2 = (q2) * (q2);
        double q2q3 = (q2) * (q3);
        double q3q3 = (q3) * (q3);
        double[][] m = new double[3][];
        m[0] = new double[3];
        m[1] = new double[3];
        m[2] = new double[3];
        m[0][0] = (2.0 * (q0q0 + q1q1)) - 1.0;
        m[1][0] = 2.0 * (q1q2 - q0q3);
        m[2][0] = 2.0 * (q1q3 + q0q2);
        m[0][1] = 2.0 * (q1q2 + q0q3);
        m[1][1] = (2.0 * (q0q0 + q2q2)) - 1.0;
        m[2][1] = 2.0 * (q2q3 - q0q1);
        m[0][2] = 2.0 * (q1q3 - q0q2);
        m[1][2] = 2.0 * (q2q3 + q0q1);
        m[2][2] = (2.0 * (q0q0 + q3q3)) - 1.0;
        return m;
    }

    public org.apache.commons.math.geometry.euclidean.threed.Vector3D applyTo(org.apache.commons.math.geometry.euclidean.threed.Vector3D u) {
        double x = u.getX();
        double y = u.getY();
        double z = u.getZ();
        double s = (((q1) * x) + ((q2) * y)) + ((q3) * z);
        return new org.apache.commons.math.geometry.euclidean.threed.Vector3D(((2 * (((q0) * ((x * (q0)) - (((q2) * z) - ((q3) * y)))) + (s * (q1)))) - x), ((2 * (((q0) * ((y * (q0)) - (((q3) * x) - ((q1) * z)))) + (s * (q2)))) - y), ((2 * (((q0) * ((z * (q0)) - (((q1) * y) - ((q2) * x)))) + (s * (q3)))) - z));
    }

    public org.apache.commons.math.geometry.euclidean.threed.Vector3D applyInverseTo(org.apache.commons.math.geometry.euclidean.threed.Vector3D u) {
        double x = u.getX();
        double y = u.getY();
        double z = u.getZ();
        double s = (((q1) * x) + ((q2) * y)) + ((q3) * z);
        double m0 = -(q0);
        return new org.apache.commons.math.geometry.euclidean.threed.Vector3D(((2 * ((m0 * ((x * m0) - (((q2) * z) - ((q3) * y)))) + (s * (q1)))) - x), ((2 * ((m0 * ((y * m0) - (((q3) * x) - ((q1) * z)))) + (s * (q2)))) - y), ((2 * ((m0 * ((z * m0) - (((q1) * y) - ((q2) * x)))) + (s * (q3)))) - z));
    }

    public org.apache.commons.math.geometry.euclidean.threed.Rotation applyTo(org.apache.commons.math.geometry.euclidean.threed.Rotation r) {
        return new org.apache.commons.math.geometry.euclidean.threed.Rotation((((r.q0) * (q0)) - ((((r.q1) * (q1)) + ((r.q2) * (q2))) + ((r.q3) * (q3)))), ((((r.q1) * (q0)) + ((r.q0) * (q1))) + (((r.q2) * (q3)) - ((r.q3) * (q2)))), ((((r.q2) * (q0)) + ((r.q0) * (q2))) + (((r.q3) * (q1)) - ((r.q1) * (q3)))), ((((r.q3) * (q0)) + ((r.q0) * (q3))) + (((r.q1) * (q2)) - ((r.q2) * (q1)))), false);
    }

    public org.apache.commons.math.geometry.euclidean.threed.Rotation applyInverseTo(org.apache.commons.math.geometry.euclidean.threed.Rotation r) {
        return new org.apache.commons.math.geometry.euclidean.threed.Rotation((((-(r.q0)) * (q0)) - ((((r.q1) * (q1)) + ((r.q2) * (q2))) + ((r.q3) * (q3)))), ((((-(r.q1)) * (q0)) + ((r.q0) * (q1))) + (((r.q2) * (q3)) - ((r.q3) * (q2)))), ((((-(r.q2)) * (q0)) + ((r.q0) * (q2))) + (((r.q3) * (q1)) - ((r.q1) * (q3)))), ((((-(r.q3)) * (q0)) + ((r.q0) * (q3))) + (((r.q1) * (q2)) - ((r.q2) * (q1)))), false);
    }

    private double[][] orthogonalizeMatrix(double[][] m, double threshold) throws org.apache.commons.math.geometry.euclidean.threed.NotARotationMatrixException {
        double[] m0 = m[0];
        double[] m1 = m[1];
        double[] m2 = m[2];
        double x00 = m0[0];
        double x01 = m0[1];
        double x02 = m0[2];
        double x10 = m1[0];
        double x11 = m1[1];
        double x12 = m1[2];
        double x20 = m2[0];
        double x21 = m2[1];
        double x22 = m2[2];
        double fn = 0;
        double fn1;
        double[][] o = new double[3][3];
        double[] o0 = o[0];
        double[] o1 = o[1];
        double[] o2 = o[2];
        int i = 0;
        while ((++i) < 11) {
            double mx00 = (((m0[0]) * x00) + ((m1[0]) * x10)) + ((m2[0]) * x20);
            double mx10 = (((m0[1]) * x00) + ((m1[1]) * x10)) + ((m2[1]) * x20);
            double mx20 = (((m0[2]) * x00) + ((m1[2]) * x10)) + ((m2[2]) * x20);
            double mx01 = (((m0[0]) * x01) + ((m1[0]) * x11)) + ((m2[0]) * x21);
            double mx11 = (((m0[1]) * x01) + ((m1[1]) * x11)) + ((m2[1]) * x21);
            double mx21 = (((m0[2]) * x01) + ((m1[2]) * x11)) + ((m2[2]) * x21);
            double mx02 = (((m0[0]) * x02) + ((m1[0]) * x12)) + ((m2[0]) * x22);
            double mx12 = (((m0[1]) * x02) + ((m1[1]) * x12)) + ((m2[1]) * x22);
            double mx22 = (((m0[2]) * x02) + ((m1[2]) * x12)) + ((m2[2]) * x22);
            o0[0] = x00 - (0.5 * ((((x00 * mx00) + (x01 * mx10)) + (x02 * mx20)) - (m0[0])));
            o0[1] = x01 - (0.5 * ((((x00 * mx01) + (x01 * mx11)) + (x02 * mx21)) - (m0[1])));
            o0[2] = x02 - (0.5 * ((((x00 * mx02) + (x01 * mx12)) + (x02 * mx22)) - (m0[2])));
            o1[0] = x10 - (0.5 * ((((x10 * mx00) + (x11 * mx10)) + (x12 * mx20)) - (m1[0])));
            o1[1] = x11 - (0.5 * ((((x10 * mx01) + (x11 * mx11)) + (x12 * mx21)) - (m1[1])));
            o1[2] = x12 - (0.5 * ((((x10 * mx02) + (x11 * mx12)) + (x12 * mx22)) - (m1[2])));
            o2[0] = x20 - (0.5 * ((((x20 * mx00) + (x21 * mx10)) + (x22 * mx20)) - (m2[0])));
            o2[1] = x21 - (0.5 * ((((x20 * mx01) + (x21 * mx11)) + (x22 * mx21)) - (m2[1])));
            o2[2] = x22 - (0.5 * ((((x20 * mx02) + (x21 * mx12)) + (x22 * mx22)) - (m2[2])));
            double corr00 = (o0[0]) - (m0[0]);
            double corr01 = (o0[1]) - (m0[1]);
            double corr02 = (o0[2]) - (m0[2]);
            double corr10 = (o1[0]) - (m1[0]);
            double corr11 = (o1[1]) - (m1[1]);
            double corr12 = (o1[2]) - (m1[2]);
            double corr20 = (o2[0]) - (m2[0]);
            double corr21 = (o2[1]) - (m2[1]);
            double corr22 = (o2[2]) - (m2[2]);
            fn1 = ((((((((corr00 * corr00) + (corr01 * corr01)) + (corr02 * corr02)) + (corr10 * corr10)) + (corr11 * corr11)) + (corr12 * corr12)) + (corr20 * corr20)) + (corr21 * corr21)) + (corr22 * corr22);
            if ((org.apache.commons.math.util.FastMath.abs((fn1 - fn))) <= threshold) {
                return o;
            }
            x00 = o0[0];
            x01 = o0[1];
            x02 = o0[2];
            x10 = o1[0];
            x11 = o1[1];
            x12 = o1[2];
            x20 = o2[0];
            x21 = o2[1];
            x22 = o2[2];
            fn = fn1;
        } 
        throw new org.apache.commons.math.geometry.euclidean.threed.NotARotationMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_ORTHOGONOLIZE_MATRIX, (i - 1));
    }

    public static double distance(org.apache.commons.math.geometry.euclidean.threed.Rotation r1, org.apache.commons.math.geometry.euclidean.threed.Rotation r2) {
        return r1.applyInverseTo(r2).getAngle();
    }
}

