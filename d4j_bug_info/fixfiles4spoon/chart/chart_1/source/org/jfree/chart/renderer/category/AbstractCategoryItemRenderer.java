package org.jfree.chart.renderer.category;


public abstract class AbstractCategoryItemRenderer extends org.jfree.chart.renderer.AbstractRenderer implements java.io.Serializable , java.lang.Cloneable , org.jfree.chart.renderer.category.CategoryItemRenderer , org.jfree.chart.util.PublicCloneable {
    private static final long serialVersionUID = 1247553218442497391L;

    private org.jfree.chart.plot.CategoryPlot plot;

    private org.jfree.chart.util.ObjectList itemLabelGeneratorList;

    private org.jfree.chart.labels.CategoryItemLabelGenerator baseItemLabelGenerator;

    private org.jfree.chart.util.ObjectList toolTipGeneratorList;

    private org.jfree.chart.labels.CategoryToolTipGenerator baseToolTipGenerator;

    private org.jfree.chart.util.ObjectList urlGeneratorList;

    private org.jfree.chart.urls.CategoryURLGenerator baseURLGenerator;

    private org.jfree.chart.labels.CategorySeriesLabelGenerator legendItemLabelGenerator;

    private org.jfree.chart.labels.CategorySeriesLabelGenerator legendItemToolTipGenerator;

    private org.jfree.chart.labels.CategorySeriesLabelGenerator legendItemURLGenerator;

    private java.util.List backgroundAnnotations;

    private java.util.List foregroundAnnotations;

    private transient int rowCount;

    private transient int columnCount;

    protected AbstractCategoryItemRenderer() {
        this.itemLabelGeneratorList = new org.jfree.chart.util.ObjectList();
        this.toolTipGeneratorList = new org.jfree.chart.util.ObjectList();
        this.urlGeneratorList = new org.jfree.chart.util.ObjectList();
        this.legendItemLabelGenerator = new org.jfree.chart.labels.StandardCategorySeriesLabelGenerator();
        this.backgroundAnnotations = new java.util.ArrayList();
        this.foregroundAnnotations = new java.util.ArrayList();
    }

    public int getPassCount() {
        return 1;
    }

    public org.jfree.chart.plot.CategoryPlot getPlot() {
        return this.plot;
    }

    public void setPlot(org.jfree.chart.plot.CategoryPlot plot) {
        if (plot == null) {
            throw new java.lang.IllegalArgumentException("Null 'plot' argument.");
        }
        this.plot = plot;
    }

    public org.jfree.chart.labels.CategoryItemLabelGenerator getItemLabelGenerator(int row, int column, boolean selected) {
        org.jfree.chart.labels.CategoryItemLabelGenerator generator = ((org.jfree.chart.labels.CategoryItemLabelGenerator) (this.itemLabelGeneratorList.get(row)));
        if (generator == null) {
            generator = this.baseItemLabelGenerator;
        }
        return generator;
    }

    public org.jfree.chart.labels.CategoryItemLabelGenerator getSeriesItemLabelGenerator(int series) {
        return ((org.jfree.chart.labels.CategoryItemLabelGenerator) (this.itemLabelGeneratorList.get(series)));
    }

    public void setSeriesItemLabelGenerator(int series, org.jfree.chart.labels.CategoryItemLabelGenerator generator) {
        setSeriesItemLabelGenerator(series, generator, true);
    }

    public void setSeriesItemLabelGenerator(int series, org.jfree.chart.labels.CategoryItemLabelGenerator generator, boolean notify) {
        this.itemLabelGeneratorList.set(series, generator);
        if (notify) {
            notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        }
    }

    public org.jfree.chart.labels.CategoryItemLabelGenerator getBaseItemLabelGenerator() {
        return this.baseItemLabelGenerator;
    }

    public void setBaseItemLabelGenerator(org.jfree.chart.labels.CategoryItemLabelGenerator generator) {
        setBaseItemLabelGenerator(generator, true);
    }

    public void setBaseItemLabelGenerator(org.jfree.chart.labels.CategoryItemLabelGenerator generator, boolean notify) {
        this.baseItemLabelGenerator = generator;
        if (notify) {
            notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        }
    }

    public org.jfree.chart.labels.CategoryToolTipGenerator getToolTipGenerator(int row, int column, boolean selected) {
        org.jfree.chart.labels.CategoryToolTipGenerator result = null;
        result = getSeriesToolTipGenerator(row);
        if (result == null) {
            result = this.baseToolTipGenerator;
        }
        return result;
    }

    public org.jfree.chart.labels.CategoryToolTipGenerator getSeriesToolTipGenerator(int series) {
        return ((org.jfree.chart.labels.CategoryToolTipGenerator) (this.toolTipGeneratorList.get(series)));
    }

    public void setSeriesToolTipGenerator(int series, org.jfree.chart.labels.CategoryToolTipGenerator generator) {
        setSeriesToolTipGenerator(series, generator, true);
    }

    public void setSeriesToolTipGenerator(int series, org.jfree.chart.labels.CategoryToolTipGenerator generator, boolean notify) {
        this.toolTipGeneratorList.set(series, generator);
        if (notify) {
            notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        }
    }

    public org.jfree.chart.labels.CategoryToolTipGenerator getBaseToolTipGenerator() {
        return this.baseToolTipGenerator;
    }

    public void setBaseToolTipGenerator(org.jfree.chart.labels.CategoryToolTipGenerator generator) {
        setBaseToolTipGenerator(generator, true);
    }

    public void setBaseToolTipGenerator(org.jfree.chart.labels.CategoryToolTipGenerator generator, boolean notify) {
        this.baseToolTipGenerator = generator;
        if (notify) {
            notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        }
    }

    public org.jfree.chart.urls.CategoryURLGenerator getURLGenerator(int row, int column, boolean selected) {
        org.jfree.chart.urls.CategoryURLGenerator generator = ((org.jfree.chart.urls.CategoryURLGenerator) (this.urlGeneratorList.get(row)));
        if (generator == null) {
            generator = this.baseURLGenerator;
        }
        return generator;
    }

    public org.jfree.chart.urls.CategoryURLGenerator getSeriesURLGenerator(int series) {
        return ((org.jfree.chart.urls.CategoryURLGenerator) (this.urlGeneratorList.get(series)));
    }

    public void setSeriesURLGenerator(int series, org.jfree.chart.urls.CategoryURLGenerator generator) {
        setSeriesURLGenerator(series, generator, true);
    }

    public void setSeriesURLGenerator(int series, org.jfree.chart.urls.CategoryURLGenerator generator, boolean notify) {
        this.urlGeneratorList.set(series, generator);
        if (notify) {
            notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        }
    }

    public org.jfree.chart.urls.CategoryURLGenerator getBaseURLGenerator() {
        return this.baseURLGenerator;
    }

    public void setBaseURLGenerator(org.jfree.chart.urls.CategoryURLGenerator generator) {
        setBaseURLGenerator(generator, true);
    }

    public void setBaseURLGenerator(org.jfree.chart.urls.CategoryURLGenerator generator, boolean notify) {
        this.baseURLGenerator = generator;
        if (notify) {
            notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        }
    }

    public void addAnnotation(org.jfree.chart.annotations.CategoryAnnotation annotation) {
        addAnnotation(annotation, Layer.FOREGROUND);
    }

    public void addAnnotation(org.jfree.chart.annotations.CategoryAnnotation annotation, org.jfree.chart.util.Layer layer) {
        if (annotation == null) {
            throw new java.lang.IllegalArgumentException("Null 'annotation' argument.");
        }
        if (layer.equals(Layer.FOREGROUND)) {
            this.foregroundAnnotations.add(annotation);
            notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        }else
            if (layer.equals(Layer.BACKGROUND)) {
                this.backgroundAnnotations.add(annotation);
                notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
            }else {
                throw new java.lang.RuntimeException("Unknown layer.");
            }

    }

    public boolean removeAnnotation(org.jfree.chart.annotations.CategoryAnnotation annotation) {
        boolean removed = this.foregroundAnnotations.remove(annotation);
        removed = removed & (this.backgroundAnnotations.remove(annotation));
        notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
        return removed;
    }

    public void removeAnnotations() {
        this.foregroundAnnotations.clear();
        this.backgroundAnnotations.clear();
        notifyListeners(new org.jfree.chart.event.RendererChangeEvent(this));
    }

    public org.jfree.chart.labels.CategorySeriesLabelGenerator getLegendItemLabelGenerator() {
        return this.legendItemLabelGenerator;
    }

    public void setLegendItemLabelGenerator(org.jfree.chart.labels.CategorySeriesLabelGenerator generator) {
        if (generator == null) {
            throw new java.lang.IllegalArgumentException("Null 'generator' argument.");
        }
        this.legendItemLabelGenerator = generator;
        fireChangeEvent();
    }

    public org.jfree.chart.labels.CategorySeriesLabelGenerator getLegendItemToolTipGenerator() {
        return this.legendItemToolTipGenerator;
    }

    public void setLegendItemToolTipGenerator(org.jfree.chart.labels.CategorySeriesLabelGenerator generator) {
        this.legendItemToolTipGenerator = generator;
        fireChangeEvent();
    }

    public org.jfree.chart.labels.CategorySeriesLabelGenerator getLegendItemURLGenerator() {
        return this.legendItemURLGenerator;
    }

    public void setLegendItemURLGenerator(org.jfree.chart.labels.CategorySeriesLabelGenerator generator) {
        this.legendItemURLGenerator = generator;
        fireChangeEvent();
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    protected org.jfree.chart.renderer.category.CategoryItemRendererState createState(org.jfree.chart.plot.PlotRenderingInfo info) {
        org.jfree.chart.renderer.category.CategoryItemRendererState state = new org.jfree.chart.renderer.category.CategoryItemRendererState(info);
        int[] visibleSeriesTemp = new int[this.rowCount];
        int visibleSeriesCount = 0;
        for (int row = 0; row < (this.rowCount); row++) {
            if (isSeriesVisible(row)) {
                visibleSeriesTemp[visibleSeriesCount] = row;
                visibleSeriesCount++;
            }
        }
        int[] visibleSeries = new int[visibleSeriesCount];
        java.lang.System.arraycopy(visibleSeriesTemp, 0, visibleSeries, 0, visibleSeriesCount);
        state.setVisibleSeriesArray(visibleSeries);
        return state;
    }

    public org.jfree.chart.renderer.category.CategoryItemRendererState initialise(java.awt.Graphics2D g2, java.awt.geom.Rectangle2D dataArea, org.jfree.chart.plot.CategoryPlot plot, org.jfree.data.category.CategoryDataset dataset, org.jfree.chart.plot.PlotRenderingInfo info) {
        setPlot(plot);
        if (dataset != null) {
            this.rowCount = dataset.getRowCount();
            this.columnCount = dataset.getColumnCount();
        }else {
            this.rowCount = 0;
            this.columnCount = 0;
        }
        org.jfree.chart.renderer.category.CategoryItemRendererState state = createState(info);
        org.jfree.data.category.CategoryDatasetSelectionState selectionState = null;
        if (dataset instanceof org.jfree.data.category.SelectableCategoryDataset) {
            org.jfree.data.category.SelectableCategoryDataset scd = ((org.jfree.data.category.SelectableCategoryDataset) (dataset));
            selectionState = scd.getSelectionState();
        }
        if ((selectionState == null) && (info != null)) {
            org.jfree.chart.ChartRenderingInfo cri = info.getOwner();
            if (cri != null) {
                org.jfree.chart.RenderingSource rs = cri.getRenderingSource();
                selectionState = ((org.jfree.data.category.CategoryDatasetSelectionState) (rs.getSelectionState(dataset)));
            }
        }
        state.setSelectionState(selectionState);
        return state;
    }

    public org.jfree.data.Range findRangeBounds(org.jfree.data.category.CategoryDataset dataset) {
        return findRangeBounds(dataset, false);
    }

    protected org.jfree.data.Range findRangeBounds(org.jfree.data.category.CategoryDataset dataset, boolean includeInterval) {
        if (dataset == null) {
            return null;
        }
        if (getDataBoundsIncludesVisibleSeriesOnly()) {
            java.util.List visibleSeriesKeys = new java.util.ArrayList();
            int seriesCount = dataset.getRowCount();
            for (int s = 0; s < seriesCount; s++) {
                if (isSeriesVisible(s)) {
                    visibleSeriesKeys.add(dataset.getRowKey(s));
                }
            }
            return org.jfree.data.general.DatasetUtilities.findRangeBounds(dataset, visibleSeriesKeys, includeInterval);
        }else {
            return org.jfree.data.general.DatasetUtilities.findRangeBounds(dataset, includeInterval);
        }
    }

    public double getItemMiddle(java.lang.Comparable rowKey, java.lang.Comparable columnKey, org.jfree.data.category.CategoryDataset dataset, org.jfree.chart.axis.CategoryAxis axis, java.awt.geom.Rectangle2D area, org.jfree.chart.util.RectangleEdge edge) {
        return axis.getCategoryMiddle(columnKey, dataset.getColumnKeys(), area, edge);
    }

    public void drawBackground(java.awt.Graphics2D g2, org.jfree.chart.plot.CategoryPlot plot, java.awt.geom.Rectangle2D dataArea) {
        plot.drawBackground(g2, dataArea);
    }

    public void drawOutline(java.awt.Graphics2D g2, org.jfree.chart.plot.CategoryPlot plot, java.awt.geom.Rectangle2D dataArea) {
        plot.drawOutline(g2, dataArea);
    }

    public void drawDomainLine(java.awt.Graphics2D g2, org.jfree.chart.plot.CategoryPlot plot, java.awt.geom.Rectangle2D dataArea, double value, java.awt.Paint paint, java.awt.Stroke stroke) {
        if (paint == null) {
            throw new java.lang.IllegalArgumentException("Null 'paint' argument.");
        }
        if (stroke == null) {
            throw new java.lang.IllegalArgumentException("Null 'stroke' argument.");
        }
        java.awt.geom.Line2D line = null;
        org.jfree.chart.plot.PlotOrientation orientation = plot.getOrientation();
        if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
            line = new java.awt.geom.Line2D.Double(dataArea.getMinX(), value, dataArea.getMaxX(), value);
        }else
            if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                line = new java.awt.geom.Line2D.Double(value, dataArea.getMinY(), value, dataArea.getMaxY());
            }

        g2.setPaint(paint);
        g2.setStroke(stroke);
        g2.draw(line);
    }

    public void drawRangeLine(java.awt.Graphics2D g2, org.jfree.chart.plot.CategoryPlot plot, org.jfree.chart.axis.ValueAxis axis, java.awt.geom.Rectangle2D dataArea, double value, java.awt.Paint paint, java.awt.Stroke stroke) {
        org.jfree.data.Range range = axis.getRange();
        if (!(range.contains(value))) {
            return;
        }
        org.jfree.chart.plot.PlotOrientation orientation = plot.getOrientation();
        java.awt.geom.Line2D line = null;
        double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
        if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
            line = new java.awt.geom.Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
        }else
            if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                line = new java.awt.geom.Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
            }

        g2.setPaint(paint);
        g2.setStroke(stroke);
        g2.draw(line);
    }

    public void drawDomainMarker(java.awt.Graphics2D g2, org.jfree.chart.plot.CategoryPlot plot, org.jfree.chart.axis.CategoryAxis axis, org.jfree.chart.plot.CategoryMarker marker, java.awt.geom.Rectangle2D dataArea) {
        java.lang.Comparable category = marker.getKey();
        org.jfree.data.category.CategoryDataset dataset = plot.getDataset(plot.getIndexOf(this));
        int columnIndex = dataset.getColumnIndex(category);
        if (columnIndex < 0) {
            return;
        }
        final java.awt.Composite savedComposite = g2.getComposite();
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, marker.getAlpha()));
        org.jfree.chart.plot.PlotOrientation orientation = plot.getOrientation();
        java.awt.geom.Rectangle2D bounds = null;
        if (marker.getDrawAsLine()) {
            double v = axis.getCategoryMiddle(columnIndex, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
            java.awt.geom.Line2D line = null;
            if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
                line = new java.awt.geom.Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
            }else
                if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                    line = new java.awt.geom.Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
                }

            g2.setPaint(marker.getPaint());
            g2.setStroke(marker.getStroke());
            g2.draw(line);
            bounds = line.getBounds2D();
        }else {
            double v0 = axis.getCategoryStart(columnIndex, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
            double v1 = axis.getCategoryEnd(columnIndex, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
            java.awt.geom.Rectangle2D area = null;
            if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
                area = new java.awt.geom.Rectangle2D.Double(dataArea.getMinX(), v0, dataArea.getWidth(), (v1 - v0));
            }else
                if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                    area = new java.awt.geom.Rectangle2D.Double(v0, dataArea.getMinY(), (v1 - v0), dataArea.getHeight());
                }

            g2.setPaint(marker.getPaint());
            g2.fill(area);
            bounds = area;
        }
        java.lang.String label = marker.getLabel();
        org.jfree.chart.util.RectangleAnchor anchor = marker.getLabelAnchor();
        if (label != null) {
            java.awt.Font labelFont = marker.getLabelFont();
            g2.setFont(labelFont);
            g2.setPaint(marker.getLabelPaint());
            java.awt.geom.Point2D coordinates = calculateDomainMarkerTextAnchorPoint(g2, orientation, dataArea, bounds, marker.getLabelOffset(), marker.getLabelOffsetType(), anchor);
            org.jfree.chart.text.TextUtilities.drawAlignedString(label, g2, ((float) (coordinates.getX())), ((float) (coordinates.getY())), marker.getLabelTextAnchor());
        }
        g2.setComposite(savedComposite);
    }

    public void drawRangeMarker(java.awt.Graphics2D g2, org.jfree.chart.plot.CategoryPlot plot, org.jfree.chart.axis.ValueAxis axis, org.jfree.chart.plot.Marker marker, java.awt.geom.Rectangle2D dataArea) {
        if (marker instanceof org.jfree.chart.plot.ValueMarker) {
            org.jfree.chart.plot.ValueMarker vm = ((org.jfree.chart.plot.ValueMarker) (marker));
            double value = vm.getValue();
            org.jfree.data.Range range = axis.getRange();
            if (!(range.contains(value))) {
                return;
            }
            final java.awt.Composite savedComposite = g2.getComposite();
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, marker.getAlpha()));
            org.jfree.chart.plot.PlotOrientation orientation = plot.getOrientation();
            double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
            java.awt.geom.Line2D line = null;
            if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
                line = new java.awt.geom.Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
            }else
                if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                    line = new java.awt.geom.Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
                }

            g2.setPaint(marker.getPaint());
            g2.setStroke(marker.getStroke());
            g2.draw(line);
            java.lang.String label = marker.getLabel();
            org.jfree.chart.util.RectangleAnchor anchor = marker.getLabelAnchor();
            if (label != null) {
                java.awt.Font labelFont = marker.getLabelFont();
                g2.setFont(labelFont);
                g2.setPaint(marker.getLabelPaint());
                java.awt.geom.Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, line.getBounds2D(), marker.getLabelOffset(), LengthAdjustmentType.EXPAND, anchor);
                org.jfree.chart.text.TextUtilities.drawAlignedString(label, g2, ((float) (coordinates.getX())), ((float) (coordinates.getY())), marker.getLabelTextAnchor());
            }
            g2.setComposite(savedComposite);
        }else
            if (marker instanceof org.jfree.chart.plot.IntervalMarker) {
                org.jfree.chart.plot.IntervalMarker im = ((org.jfree.chart.plot.IntervalMarker) (marker));
                double start = im.getStartValue();
                double end = im.getEndValue();
                org.jfree.data.Range range = axis.getRange();
                if (!(range.intersects(start, end))) {
                    return;
                }
                final java.awt.Composite savedComposite = g2.getComposite();
                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, marker.getAlpha()));
                double start2d = axis.valueToJava2D(start, dataArea, plot.getRangeAxisEdge());
                double end2d = axis.valueToJava2D(end, dataArea, plot.getRangeAxisEdge());
                double low = java.lang.Math.min(start2d, end2d);
                double high = java.lang.Math.max(start2d, end2d);
                org.jfree.chart.plot.PlotOrientation orientation = plot.getOrientation();
                java.awt.geom.Rectangle2D rect = null;
                if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
                    low = java.lang.Math.max(low, dataArea.getMinX());
                    high = java.lang.Math.min(high, dataArea.getMaxX());
                    rect = new java.awt.geom.Rectangle2D.Double(low, dataArea.getMinY(), (high - low), dataArea.getHeight());
                }else
                    if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                        low = java.lang.Math.max(low, dataArea.getMinY());
                        high = java.lang.Math.min(high, dataArea.getMaxY());
                        rect = new java.awt.geom.Rectangle2D.Double(dataArea.getMinX(), low, dataArea.getWidth(), (high - low));
                    }

                java.awt.Paint p = marker.getPaint();
                if (p instanceof java.awt.GradientPaint) {
                    java.awt.GradientPaint gp = ((java.awt.GradientPaint) (p));
                    org.jfree.chart.util.GradientPaintTransformer t = im.getGradientPaintTransformer();
                    if (t != null) {
                        gp = t.transform(gp, rect);
                    }
                    g2.setPaint(gp);
                }else {
                    g2.setPaint(p);
                }
                g2.fill(rect);
                if (((im.getOutlinePaint()) != null) && ((im.getOutlineStroke()) != null)) {
                    if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                        java.awt.geom.Line2D line = new java.awt.geom.Line2D.Double();
                        double x0 = dataArea.getMinX();
                        double x1 = dataArea.getMaxX();
                        g2.setPaint(im.getOutlinePaint());
                        g2.setStroke(im.getOutlineStroke());
                        if (range.contains(start)) {
                            line.setLine(x0, start2d, x1, start2d);
                            g2.draw(line);
                        }
                        if (range.contains(end)) {
                            line.setLine(x0, end2d, x1, end2d);
                            g2.draw(line);
                        }
                    }else {
                        java.awt.geom.Line2D line = new java.awt.geom.Line2D.Double();
                        double y0 = dataArea.getMinY();
                        double y1 = dataArea.getMaxY();
                        g2.setPaint(im.getOutlinePaint());
                        g2.setStroke(im.getOutlineStroke());
                        if (range.contains(start)) {
                            line.setLine(start2d, y0, start2d, y1);
                            g2.draw(line);
                        }
                        if (range.contains(end)) {
                            line.setLine(end2d, y0, end2d, y1);
                            g2.draw(line);
                        }
                    }
                }
                java.lang.String label = marker.getLabel();
                org.jfree.chart.util.RectangleAnchor anchor = marker.getLabelAnchor();
                if (label != null) {
                    java.awt.Font labelFont = marker.getLabelFont();
                    g2.setFont(labelFont);
                    g2.setPaint(marker.getLabelPaint());
                    java.awt.geom.Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, rect, marker.getLabelOffset(), marker.getLabelOffsetType(), anchor);
                    org.jfree.chart.text.TextUtilities.drawAlignedString(label, g2, ((float) (coordinates.getX())), ((float) (coordinates.getY())), marker.getLabelTextAnchor());
                }
                g2.setComposite(savedComposite);
            }

    }

    protected java.awt.geom.Point2D calculateDomainMarkerTextAnchorPoint(java.awt.Graphics2D g2, org.jfree.chart.plot.PlotOrientation orientation, java.awt.geom.Rectangle2D dataArea, java.awt.geom.Rectangle2D markerArea, org.jfree.chart.util.RectangleInsets markerOffset, org.jfree.chart.util.LengthAdjustmentType labelOffsetType, org.jfree.chart.util.RectangleAnchor anchor) {
        java.awt.geom.Rectangle2D anchorRect = null;
        if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetType);
        }else
            if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetType, LengthAdjustmentType.CONTRACT);
            }

        return org.jfree.chart.util.RectangleAnchor.coordinates(anchorRect, anchor);
    }

    protected java.awt.geom.Point2D calculateRangeMarkerTextAnchorPoint(java.awt.Graphics2D g2, org.jfree.chart.plot.PlotOrientation orientation, java.awt.geom.Rectangle2D dataArea, java.awt.geom.Rectangle2D markerArea, org.jfree.chart.util.RectangleInsets markerOffset, org.jfree.chart.util.LengthAdjustmentType labelOffsetType, org.jfree.chart.util.RectangleAnchor anchor) {
        java.awt.geom.Rectangle2D anchorRect = null;
        if (orientation == (org.jfree.chart.plot.PlotOrientation.HORIZONTAL)) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetType, LengthAdjustmentType.CONTRACT);
        }else
            if (orientation == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetType);
            }

        return org.jfree.chart.util.RectangleAnchor.coordinates(anchorRect, anchor);
    }

    public org.jfree.chart.LegendItem getLegendItem(int datasetIndex, int series) {
        org.jfree.chart.plot.CategoryPlot p = getPlot();
        if (p == null) {
            return null;
        }
        if ((!(isSeriesVisible(series))) || (!(isSeriesVisibleInLegend(series)))) {
            return null;
        }
        org.jfree.data.category.CategoryDataset dataset = p.getDataset(datasetIndex);
        java.lang.String label = this.legendItemLabelGenerator.generateLabel(dataset, series);
        java.lang.String description = label;
        java.lang.String toolTipText = null;
        if ((this.legendItemToolTipGenerator) != null) {
            toolTipText = this.legendItemToolTipGenerator.generateLabel(dataset, series);
        }
        java.lang.String urlText = null;
        if ((this.legendItemURLGenerator) != null) {
            urlText = this.legendItemURLGenerator.generateLabel(dataset, series);
        }
        java.awt.Shape shape = lookupLegendShape(series);
        java.awt.Paint paint = lookupSeriesPaint(series);
        java.awt.Paint outlinePaint = lookupSeriesOutlinePaint(series);
        java.awt.Stroke outlineStroke = lookupSeriesOutlineStroke(series);
        org.jfree.chart.LegendItem item = new org.jfree.chart.LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
        item.setLabelFont(lookupLegendTextFont(series));
        java.awt.Paint labelPaint = lookupLegendTextPaint(series);
        if (labelPaint != null) {
            item.setLabelPaint(labelPaint);
        }
        item.setSeriesKey(dataset.getRowKey(series));
        item.setSeriesIndex(series);
        item.setDataset(dataset);
        item.setDatasetIndex(datasetIndex);
        return item;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == (this)) {
            return true;
        }
        if (!(obj instanceof org.jfree.chart.renderer.category.AbstractCategoryItemRenderer)) {
            return false;
        }
        org.jfree.chart.renderer.category.AbstractCategoryItemRenderer that = ((org.jfree.chart.renderer.category.AbstractCategoryItemRenderer) (obj));
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.itemLabelGeneratorList, that.itemLabelGeneratorList))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.baseItemLabelGenerator, that.baseItemLabelGenerator))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.toolTipGeneratorList, that.toolTipGeneratorList))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.baseToolTipGenerator, that.baseToolTipGenerator))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.urlGeneratorList, that.urlGeneratorList))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.baseURLGenerator, that.baseURLGenerator))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.legendItemLabelGenerator, that.legendItemLabelGenerator))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.legendItemToolTipGenerator, that.legendItemToolTipGenerator))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.legendItemURLGenerator, that.legendItemURLGenerator))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.backgroundAnnotations, that.backgroundAnnotations))) {
            return false;
        }
        if (!(org.jfree.chart.util.ObjectUtilities.equal(this.foregroundAnnotations, that.foregroundAnnotations))) {
            return false;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    public org.jfree.chart.plot.DrawingSupplier getDrawingSupplier() {
        org.jfree.chart.plot.DrawingSupplier result = null;
        org.jfree.chart.plot.CategoryPlot cp = getPlot();
        if (cp != null) {
            result = cp.getDrawingSupplier();
        }
        return result;
    }

    protected void updateCrosshairValues(org.jfree.chart.plot.CategoryCrosshairState crosshairState, java.lang.Comparable rowKey, java.lang.Comparable columnKey, double value, int datasetIndex, double transX, double transY, org.jfree.chart.plot.PlotOrientation orientation) {
        if (orientation == null) {
            throw new java.lang.IllegalArgumentException("Null 'orientation' argument.");
        }
        if (crosshairState != null) {
            if (this.plot.isRangeCrosshairLockedOnData()) {
                crosshairState.updateCrosshairPoint(rowKey, columnKey, value, datasetIndex, transX, transY, orientation);
            }else {
                crosshairState.updateCrosshairX(rowKey, columnKey, datasetIndex, transX, orientation);
            }
        }
    }

    protected void drawItemLabel(java.awt.Graphics2D g2, org.jfree.chart.plot.PlotOrientation orientation, org.jfree.data.category.CategoryDataset dataset, int row, int column, boolean selected, double x, double y, boolean negative) {
        org.jfree.chart.labels.CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column, selected);
        if (generator != null) {
            java.awt.Font labelFont = getItemLabelFont(row, column, selected);
            java.awt.Paint paint = getItemLabelPaint(row, column, selected);
            g2.setFont(labelFont);
            g2.setPaint(paint);
            java.lang.String label = generator.generateLabel(dataset, row, column);
            org.jfree.chart.labels.ItemLabelPosition position = null;
            if (!negative) {
                position = getPositiveItemLabelPosition(row, column, selected);
            }else {
                position = getNegativeItemLabelPosition(row, column, selected);
            }
            java.awt.geom.Point2D anchorPoint = calculateLabelAnchorPoint(position.getItemLabelAnchor(), x, y, orientation);
            org.jfree.chart.text.TextUtilities.drawRotatedString(label, g2, ((float) (anchorPoint.getX())), ((float) (anchorPoint.getY())), position.getTextAnchor(), position.getAngle(), position.getRotationAnchor());
        }
    }

    public void drawAnnotations(java.awt.Graphics2D g2, java.awt.geom.Rectangle2D dataArea, org.jfree.chart.axis.CategoryAxis domainAxis, org.jfree.chart.axis.ValueAxis rangeAxis, org.jfree.chart.util.Layer layer, org.jfree.chart.plot.PlotRenderingInfo info) {
        java.util.Iterator iterator = null;
        if (layer.equals(Layer.FOREGROUND)) {
            iterator = this.foregroundAnnotations.iterator();
        }else
            if (layer.equals(Layer.BACKGROUND)) {
                iterator = this.backgroundAnnotations.iterator();
            }else {
                throw new java.lang.RuntimeException("Unknown layer.");
            }

        while (iterator.hasNext()) {
            org.jfree.chart.annotations.CategoryAnnotation annotation = ((org.jfree.chart.annotations.CategoryAnnotation) (iterator.next()));
            annotation.draw(g2, this.plot, dataArea, domainAxis, rangeAxis, 0, info);
        } 
    }

    public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        org.jfree.chart.renderer.category.AbstractCategoryItemRenderer clone = ((org.jfree.chart.renderer.category.AbstractCategoryItemRenderer) (super.clone()));
        if ((this.itemLabelGeneratorList) != null) {
            clone.itemLabelGeneratorList = ((org.jfree.chart.util.ObjectList) (this.itemLabelGeneratorList.clone()));
        }
        if ((this.baseItemLabelGenerator) != null) {
            if ((this.baseItemLabelGenerator) instanceof org.jfree.chart.util.PublicCloneable) {
                org.jfree.chart.util.PublicCloneable pc = ((org.jfree.chart.util.PublicCloneable) (this.baseItemLabelGenerator));
                clone.baseItemLabelGenerator = ((org.jfree.chart.labels.CategoryItemLabelGenerator) (pc.clone()));
            }else {
                throw new java.lang.CloneNotSupportedException("ItemLabelGenerator not cloneable.");
            }
        }
        if ((this.toolTipGeneratorList) != null) {
            clone.toolTipGeneratorList = ((org.jfree.chart.util.ObjectList) (this.toolTipGeneratorList.clone()));
        }
        if ((this.baseToolTipGenerator) != null) {
            if ((this.baseToolTipGenerator) instanceof org.jfree.chart.util.PublicCloneable) {
                org.jfree.chart.util.PublicCloneable pc = ((org.jfree.chart.util.PublicCloneable) (this.baseToolTipGenerator));
                clone.baseToolTipGenerator = ((org.jfree.chart.labels.CategoryToolTipGenerator) (pc.clone()));
            }else {
                throw new java.lang.CloneNotSupportedException("Base tool tip generator not cloneable.");
            }
        }
        if ((this.urlGeneratorList) != null) {
            clone.urlGeneratorList = ((org.jfree.chart.util.ObjectList) (this.urlGeneratorList.clone()));
        }
        if ((this.baseURLGenerator) != null) {
            if ((this.baseURLGenerator) instanceof org.jfree.chart.util.PublicCloneable) {
                org.jfree.chart.util.PublicCloneable pc = ((org.jfree.chart.util.PublicCloneable) (this.baseURLGenerator));
                clone.baseURLGenerator = ((org.jfree.chart.urls.CategoryURLGenerator) (pc.clone()));
            }else {
                throw new java.lang.CloneNotSupportedException("Base item URL generator not cloneable.");
            }
        }
        if ((this.legendItemLabelGenerator) instanceof org.jfree.chart.util.PublicCloneable) {
            clone.legendItemLabelGenerator = ((org.jfree.chart.labels.CategorySeriesLabelGenerator) (org.jfree.chart.util.ObjectUtilities.clone(this.legendItemLabelGenerator)));
        }
        if ((this.legendItemToolTipGenerator) instanceof org.jfree.chart.util.PublicCloneable) {
            clone.legendItemToolTipGenerator = ((org.jfree.chart.labels.CategorySeriesLabelGenerator) (org.jfree.chart.util.ObjectUtilities.clone(this.legendItemToolTipGenerator)));
        }
        if ((this.legendItemURLGenerator) instanceof org.jfree.chart.util.PublicCloneable) {
            clone.legendItemURLGenerator = ((org.jfree.chart.labels.CategorySeriesLabelGenerator) (org.jfree.chart.util.ObjectUtilities.clone(this.legendItemURLGenerator)));
        }
        return clone;
    }

    protected org.jfree.chart.axis.CategoryAxis getDomainAxis(org.jfree.chart.plot.CategoryPlot plot, org.jfree.data.category.CategoryDataset dataset) {
        int datasetIndex = plot.indexOf(dataset);
        return plot.getDomainAxisForDataset(datasetIndex);
    }

    protected org.jfree.chart.axis.ValueAxis getRangeAxis(org.jfree.chart.plot.CategoryPlot plot, int index) {
        org.jfree.chart.axis.ValueAxis result = plot.getRangeAxis(index);
        if (result == null) {
            result = plot.getRangeAxis();
        }
        return result;
    }

    public org.jfree.chart.LegendItemCollection getLegendItems() {
        org.jfree.chart.LegendItemCollection result = new org.jfree.chart.LegendItemCollection();
        if ((this.plot) == null) {
            return result;
        }
        int index = this.plot.getIndexOf(this);
        org.jfree.data.category.CategoryDataset dataset = this.plot.getDataset(index);
        if (dataset == null) {
            return result;
        }
        int seriesCount = dataset.getRowCount();
        if (plot.getRowRenderingOrder().equals(SortOrder.ASCENDING)) {
            for (int i = 0; i < seriesCount; i++) {
                if (isSeriesVisibleInLegend(i)) {
                    org.jfree.chart.LegendItem item = getLegendItem(index, i);
                    if (item != null) {
                        result.add(item);
                    }
                }
            }
        }else {
            for (int i = seriesCount - 1; i >= 0; i--) {
                if (isSeriesVisibleInLegend(i)) {
                    org.jfree.chart.LegendItem item = getLegendItem(index, i);
                    if (item != null) {
                        result.add(item);
                    }
                }
            }
        }
        return result;
    }

    protected void addEntity(org.jfree.chart.entity.EntityCollection entities, java.awt.Shape hotspot, org.jfree.data.category.CategoryDataset dataset, int row, int column, boolean selected) {
        if (hotspot == null) {
            throw new java.lang.IllegalArgumentException("Null 'hotspot' argument.");
        }
        addEntity(entities, hotspot, dataset, row, column, selected, 0.0, 0.0);
    }

    protected void addEntity(org.jfree.chart.entity.EntityCollection entities, java.awt.Shape hotspot, org.jfree.data.category.CategoryDataset dataset, int row, int column, boolean selected, double entityX, double entityY) {
        if (!(getItemCreateEntity(row, column, selected))) {
            return;
        }
        java.awt.Shape s = hotspot;
        if (hotspot == null) {
            double r = getDefaultEntityRadius();
            double w = r * 2;
            if ((getPlot().getOrientation()) == (org.jfree.chart.plot.PlotOrientation.VERTICAL)) {
                s = new java.awt.geom.Ellipse2D.Double((entityX - r), (entityY - r), w, w);
            }else {
                s = new java.awt.geom.Ellipse2D.Double((entityY - r), (entityX - r), w, w);
            }
        }
        java.lang.String tip = null;
        org.jfree.chart.labels.CategoryToolTipGenerator generator = getToolTipGenerator(row, column, selected);
        if (generator != null) {
            tip = generator.generateToolTip(dataset, row, column);
        }
        java.lang.String url = null;
        org.jfree.chart.urls.CategoryURLGenerator urlster = getURLGenerator(row, column, selected);
        if (urlster != null) {
            url = urlster.generateURL(dataset, row, column);
        }
        org.jfree.chart.entity.CategoryItemEntity entity = new org.jfree.chart.entity.CategoryItemEntity(s, tip, url, dataset, dataset.getRowKey(row), dataset.getColumnKey(column));
        entities.add(entity);
    }

    public java.awt.Shape createHotSpotShape(java.awt.Graphics2D g2, java.awt.geom.Rectangle2D dataArea, org.jfree.chart.plot.CategoryPlot plot, org.jfree.chart.axis.CategoryAxis domainAxis, org.jfree.chart.axis.ValueAxis rangeAxis, org.jfree.data.category.CategoryDataset dataset, int row, int column, boolean selected, org.jfree.chart.renderer.category.CategoryItemRendererState state) {
        throw new java.lang.RuntimeException("Not implemented.");
    }

    public java.awt.geom.Rectangle2D createHotSpotBounds(java.awt.Graphics2D g2, java.awt.geom.Rectangle2D dataArea, org.jfree.chart.plot.CategoryPlot plot, org.jfree.chart.axis.CategoryAxis domainAxis, org.jfree.chart.axis.ValueAxis rangeAxis, org.jfree.data.category.CategoryDataset dataset, int row, int column, boolean selected, org.jfree.chart.renderer.category.CategoryItemRendererState state, java.awt.geom.Rectangle2D result) {
        if (result == null) {
            result = new java.awt.Rectangle();
        }
        java.lang.Comparable key = dataset.getColumnKey(column);
        java.lang.Number y = dataset.getValue(row, column);
        if (y == null) {
            return null;
        }
        double xx = domainAxis.getCategoryMiddle(key, plot.getCategoriesForAxis(domainAxis), dataArea, plot.getDomainAxisEdge());
        double yy = rangeAxis.valueToJava2D(y.doubleValue(), dataArea, plot.getRangeAxisEdge());
        result.setRect((xx - 2), (yy - 2), 4, 4);
        return result;
    }

    public boolean hitTest(double xx, double yy, java.awt.Graphics2D g2, java.awt.geom.Rectangle2D dataArea, org.jfree.chart.plot.CategoryPlot plot, org.jfree.chart.axis.CategoryAxis domainAxis, org.jfree.chart.axis.ValueAxis rangeAxis, org.jfree.data.category.CategoryDataset dataset, int row, int column, boolean selected, org.jfree.chart.renderer.category.CategoryItemRendererState state) {
        java.awt.geom.Rectangle2D bounds = createHotSpotBounds(g2, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, selected, state, null);
        if (bounds == null) {
            return false;
        }
        return bounds.contains(xx, yy);
    }
}

