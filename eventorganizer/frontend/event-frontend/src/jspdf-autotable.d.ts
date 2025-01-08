declare module 'jspdf-autotable' {
  import jsPDF from 'jspdf';

  interface AutoTableConfig {
    head?: any[][];
    body?: any[][];
    startY?: number;
    theme?: 'striped' | 'plain' | 'grid';
    headStyles?: {
      fillColor?: number[];
      textColor?: number[];
      fontSize?: number;
    };
    margin?: {
      left?: number;
      right?: number;
      top?: number;
      bottom?: number;
    };
  }

  export default function autoTable(doc: jsPDF, config: AutoTableConfig): void;
}
